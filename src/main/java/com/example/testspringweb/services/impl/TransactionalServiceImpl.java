package com.example.testspringweb.services.impl;

import com.example.testspringweb.common.CommonConstant;
import com.example.testspringweb.common.CommonUtils;
import com.example.testspringweb.common.TransactionalConstant;
import com.example.testspringweb.dto.TransactionalHistoryUser;
import com.example.testspringweb.dto.UserDTOResponse;
import com.example.testspringweb.exption.InvalidException;
import com.example.testspringweb.models.House;
import com.example.testspringweb.models.Transactional;
import com.example.testspringweb.repository.HouseRepository;
import com.example.testspringweb.repository.TransactionalRepository;
import com.example.testspringweb.services.CommonService;
import com.example.testspringweb.services.HouseService;
import com.example.testspringweb.services.TransactionalService;
import com.example.testspringweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionalServiceImpl implements TransactionalService {

    @Autowired
    private UserService userService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private TransactionalRepository transactionalRepository;

    @Autowired
    private HouseRepository houseRepository;

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
        long totalDay = validateDate(transactionalRequest.getStartTime(), transactionalRequest.getEndTime());
        checkDateOfPast(transactionalRequest.getStartTime());
        House house = houseService.getDetailHouse(transactionalRequest.getIdHouse());
        UserDTOResponse userHost = userService.getDetailUser(house.getIdUser());
        if (CommonConstant.INACTIVE.equals(userHost.getStatus())) {
            throw new InvalidException("Bạn không thể thuê căn nhà này bởi vì chủ nhà đã ngừng hoạt động");
        }
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
        transactionalRequest.setTotalDay((int) totalDay);
        validateTransactionalProgress(transactionalRequest);

        BigDecimal totalAmount = getTotalAmount(transactionalRequest, TransactionalConstant.CREATE);
        transactionalRequest.setTotalAmountExpected(totalAmount);

        return transactionalRepository.save(transactionalRequest);
    }

    private void checkDateOfPast(Date startTime) {
        if (Objects.isNull(startTime)) return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dateWithZeroTime = calendar.getTime();
        if (dateWithZeroTime.compareTo(startTime) > 0) {
            throw new InvalidException("Bạn không thể đặt thuê nhà ở quá khứ");
        }
    }

    private void validateTransactionalProgress(Transactional transactionalRequest) {
        if (transactionalRequest.getIdUserGuest().equals(transactionalRequest.getIdUserHost())) {
            throw new InvalidException("Không thể thuê nhà của chính bạn");
        }
        List<Transactional> transactionalList = transactionalRepository.
                getAllTransactionalByHouseId(transactionalRequest.getIdHouse());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String startDateRequest = simpleDateFormat.format(transactionalRequest.getStartTime());
        String endDateRequest = simpleDateFormat.format(transactionalRequest.getEndTime());
        List<String> listDateRangeLarge = CommonUtils.checkDateRange(startDateRequest, endDateRequest);
        for (Transactional transactional : transactionalList) {
            if (Objects.isNull(transactional.getStartTime())) continue;
            if (Objects.isNull(transactional.getEndTime())) continue;
            String startDate = simpleDateFormat.format(transactional.getStartTime());
            String endDate = simpleDateFormat.format(transactional.getEndTime());

            if (listDateRangeLarge.contains(startDate) || listDateRangeLarge.contains(endDate)) {
                throw new InvalidException("Đã có người thuê từ ngày: " + startDate + " đến ngày: " + endDate);
            }
            List<String> listDateRange = CommonUtils.checkDateRange(startDate, endDate);
            if (listDateRange.contains(startDateRequest) || listDateRange.contains(endDateRequest)) {
                throw new InvalidException("Đã có người thuê từ ngày: " + startDate + " đến ngày: " + endDate);
            }

        }
    }

    private long validateDate(Date startDate, Date endDate) {
        long numberDifference = commonService.getDateDifference(startDate, endDate);
        if (numberDifference <= 0) {
            throw new InvalidException("Ngày kết thúc phải lớn hơn ngày bắt đầu");
        }
        return numberDifference;
    }

    @Override
    public Transactional updateTransactional(Long transactionalId, String status) {
        if (TransactionalConstant.CANCELED.equalsIgnoreCase(status)) {
            Transactional transactional = getDetailTransactional(transactionalId);
            transactional.setStatus(TransactionalConstant.CANCELED);
            transactional.setUpdatedAt(new Date());
            transactional.setCancelReason("By host");
            return transactionalRepository.save(transactional);
        }
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
            transactional.setCheckOutTime(new Date());
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
            transactional.setStatus(TransactionalConstant.RENTED);
            return transactionalRepository.save(transactional);
        } else {
            throw new InvalidException("Bạn không phải người thuê căn nhà này");
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
    public Page<TransactionalHistoryUser> getAllTransactionalByUser(Long idUser, Pageable pageable) {
        List<Transactional> transactionalList = transactionalRepository.getAllTransactionalByUser(idUser);
        List<Long> idHouseList = transactionalList.stream().map(Transactional::getIdHouse).distinct().toList();
        List<TransactionalHistoryUser> transactionalHistoryUsers = new ArrayList<>();
        List<House> houseList = houseRepository.getAllByIdIn(idHouseList);

        if (CollectionUtils.isEmpty(houseList)) return new PageImpl<>(List.of());
        for (House house : houseList) {
            Long idHouse = house.getId();
            TransactionalHistoryUser transactionalHistoryUser = new TransactionalHistoryUser();
            transactionalHistoryUser.setHouse(house);
            transactionalHistoryUser.setTransactionalList(transactionalList.stream()
                    .filter(item -> item.getIdHouse().equals(idHouse)).collect(Collectors.toList()));
            transactionalHistoryUsers.add(transactionalHistoryUser);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), transactionalHistoryUsers.size());
        List<TransactionalHistoryUser> pagedList = new ArrayList<>();
        if (start < transactionalHistoryUsers.size()) {
            pagedList = transactionalHistoryUsers.subList(start, end);
        }
        return new PageImpl<>(pagedList, pageable, transactionalHistoryUsers.size());
    }

    @Override
    public BigDecimal totalMonthly(Long userId, String month) {
        boolean isMonthOfYear = checkMonthDate(month);
        if (!isMonthOfYear) return BigDecimal.ZERO;
        List<Transactional> getTotalMonthlyByUserId = transactionalRepository
                .getTotalMonthlyByUserId(month, userId, TransactionalConstant.COMPLETED);
        BigDecimal total = BigDecimal.ZERO;
        for (Transactional transactional : getTotalMonthlyByUserId) {
            total = total.add(transactional.getTotalAmountActual());
        }
        return total;
    }

    @Override
    public boolean cancelRental(Long transactionalId, Long userId) {
        UserDTOResponse userLogin = userService.getDetailUser(userId);
        Transactional transactional = getDetailTransactional(transactionalId);
        if (!transactional.getIdUserGuest().equals(userLogin.getId())) {
            throw new InvalidException("Bạn không phải người thuê căn nhà này");
        }
        if (TransactionalConstant.CANCELED.equals(transactional.getStatus())) {
            throw new InvalidException("Bạn đã hủy thuê căn nhà này rồi");
        }
        Date endTime = transactional.getEndTime();
        Date date = new Date();
        long dateDifference = commonService.getDateDifference(endTime, date);
        if (dateDifference > 1) {
            throw new InvalidException("Bạn chỉ được phép hủy đơn trong ngày đầu tiên đăng kí");
        }
        try {
            transactional.setStatus(TransactionalConstant.CANCELED);
            transactional.setCancelReason("By user");
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
        if (TransactionalConstant.CREATE.equals(type)) {
            checkoutTime = transactional.getEndTime();
        } else {
            checkoutTime = transactional.getCheckOutTime();
        }
        Date startTime = transactional.getStartTime();
        long dateDifference = commonService.getDateDifference(startTime, checkoutTime);
        BigDecimal dateDifferenceBigDecimal = BigDecimal.valueOf(dateDifference + 1);
        return price.multiply(dateDifferenceBigDecimal);
    }
}
