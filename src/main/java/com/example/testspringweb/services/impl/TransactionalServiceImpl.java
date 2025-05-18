package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.TransactionalConstant;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.exption.InvalidException;
import com.example.testspringweb.models.House;
import com.example.testspringweb.models.Transactional;
import com.example.testspringweb.repository.TransactionalRepository;
import com.example.testspringweb.services.CommonService;
import com.example.testspringweb.services.HouseService;
import com.example.testspringweb.services.TransactionalService;
import com.example.testspringweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionalServiceImpl implements TransactionalService {

    @Autowired
    private UserService userService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private TransactionalRepository transactionalRepository;

    private final CommonService commonService = new CommonService();

    @Override
    public Transactional getDetailTransactional(Long transactionalId) {
        Optional<Transactional> transactional = transactionalRepository.findById(transactionalId);
        if (transactional.isEmpty()) {
            throw new InvalidException("Invalid transactional");
        }
        return transactional.get();
    }

    @Override
    public Transactional createTransactional(Transactional transactionalRequest) {
        validateDate(transactionalRequest.getStartTime(), transactionalRequest.getEndTime());
        House house = houseService.getDetailHouse(transactionalRequest.getIdHouse());
        UserDTOResponse userHost = userService.getDetailUser(house.getIdUser());
        UserDTOResponse userGuest = userService.getDetailUser(transactionalRequest.getIdUserGuest());
        transactionalRequest.setCreatedAt(new Date());
        transactionalRequest.setUpdatedAt(new Date());
        transactionalRequest.setIdHouse(house.getId());
        transactionalRequest.setIdUserHost(userHost.getId());
        transactionalRequest.setIdUserGuest(userGuest.getId());
        transactionalRequest.setFullNameUserGuest(userGuest.getFullName());
        transactionalRequest.setStatus(TransactionalConstant.PROCESSING);
        transactionalRequest.setStartTime(transactionalRequest.getStartTime());
        transactionalRequest.setEndTime(transactionalRequest.getEndTime());

        validateTransactionalProgress(transactionalRequest);

        BigDecimal totalAmount = getTotalAmount(transactionalRequest, TransactionalConstant.CREATE);
        transactionalRequest.setTotalAmountExpected(totalAmount);

        return transactionalRepository.save(transactionalRequest);
    }

    private void validateTransactionalProgress(Transactional transactionalRequest) {
        if (transactionalRequest.getIdUserGuest().equals(transactionalRequest.getIdUserHost())) {
            throw new InvalidException("Không thể thuê nhà của chính bạn");
        }
        List<Transactional> transactionalList = transactionalRepository.
                getAllTransactionalByHouseId(transactionalRequest.getIdHouse());
        Date dateRequest = transactionalRequest.getEndTime();
        for (Transactional transactional : transactionalList) {
            if (Objects.isNull(transactional.getStartTime()) || Objects.isNull(transactional.getEndTime())) {
                throw new InvalidException("Ngày không tồn tại");
            }
            Date dateInProgress = transactional.getEndTime();
            try {
                validateDate(dateInProgress, dateRequest);
            } catch (Exception e) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String startDate = simpleDateFormat.format(transactional.getStartTime());
                String endDate = simpleDateFormat.format(dateInProgress);
                throw new InvalidException("Đã có người thuê từ ngày: " + startDate + " đến ngày: " + endDate);
            }
        }
    }

    private void validateDate(Date startDate, Date endDate) {
        long numberDifference = commonService.getDateDifference(startDate, endDate);
        if (numberDifference <= 0) {
            throw new InvalidException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }
    }

    @Override
    public Transactional updateTransactional(Long transactionalId, String status) {
        if (TransactionalConstant.CONFIRM.equalsIgnoreCase(status)) {
            Transactional transactional = getDetailTransactional(transactionalId);
            transactional.setStatus(TransactionalConstant.CONFIRM);
            transactional.setUpdatedAt(new Date());
            return transactionalRepository.save(transactional);
        }
        if (TransactionalConstant.COMPLETED.equalsIgnoreCase(status)) {
            Transactional transactional = getDetailTransactional(transactionalId);
            transactional.setStatus(TransactionalConstant.COMPLETED);
            transactional.setUpdatedAt(new Date());
            BigDecimal totalAmount = getTotalAmount(transactional, TransactionalConstant.UPDATE);
            transactional.setTotalAmountActual(totalAmount);
            return transactionalRepository.save(transactional);
        }
        throw new InvalidException("Không có trạng thái này");
    }

    @Override
    public Transactional checkIn(Long transactionalId, Long userId) {
        UserDTOResponse user = userService.getDetailUser(userId);
        Transactional transactional = getDetailTransactional(transactionalId);
        if (transactional.getIdUserGuest().equals(user.getId())) {
            transactional.setUpdatedAt(new Date());
            transactional.setCheckInTime(new Date());
            return transactionalRepository.save(transactional);
        } else {
            throw new InvalidException("bạn không phải người thuê căn nhà này");
        }
    }

    @Override
    public Page<Transactional> getAllTransactionalPage(Pageable pageable) {
        return transactionalRepository.getAllTransactionalPage(pageable);
    }

    @Override
    public Page<Transactional> getAllTransactionalByHouseId(Long houseId, Pageable pageable) {
        return transactionalRepository.getAllTransactionalByHouseId(houseId, pageable);
    }

    @Override
    public BigDecimal totalMonthly(Long userId, String month) {
        boolean isMonthOfYear = checkMonthDate(month);
        if (!isMonthOfYear) return BigDecimal.ZERO;
        List<Transactional> getTotalMonthlyByUserId = transactionalRepository
                .getTotalMonthlyByUserId(month, userId, TransactionalConstant.COMPLETED);
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < getTotalMonthlyByUserId.size(); i++) {
            total = total.add(getTotalMonthlyByUserId.get(i).getTotalAmountActual());
        }
        return total;
    }

    @Override
    public boolean cancelRental(Long transactionalId, Long userId) {
        UserDTOResponse userLogin = userService.getDetailUser(userId);
        Transactional transactional = getDetailTransactional(transactionalId);
        if (!transactional.getIdUserGuest().equals(userLogin.getId())) {
            throw new InvalidException("bạn không phải người thuê căn nhà này");
        }
        Date endTime = transactional.getEndTime();
        Date date = new Date();
        long dateDifference = commonService.getDateDifference(endTime, date);
        if (dateDifference > 1) {
            return false;
        }
        try {
            transactional.setStatus(TransactionalConstant.CANCELED);
            transactionalRepository.save(transactional);
        } catch (Exception e) {
            throw new InvalidException(e.getMessage());
        }
        return true;
    }

    private boolean checkMonthDate(String month) {
        List<String> monthOfYear = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        return monthOfYear.contains(month);
    }

    private BigDecimal getTotalAmount(Transactional transactional, String type) {
        House house = houseService.getDetailHouse(transactional.getIdHouse());
        BigDecimal price = house.getPrice();
        Date checkoutTime;
        if ("create".equals(type)) {
            checkoutTime = transactional.getEndTime();
        } else {
            checkoutTime = transactional.getCheckOutTime();
        }
        Date startTime = transactional.getStartTime();
        long dateDifference = commonService.getDateDifference(startTime, checkoutTime);
        BigDecimal dateDifferenceBigDecimal = BigDecimal.valueOf(dateDifference + 1);
        BigDecimal amountPayable = price.multiply(dateDifferenceBigDecimal);
        return amountPayable;
    }
}
