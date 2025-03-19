package com.example.testspringweb.services;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonService {

    public Map<String, String> validateInput(BindingResult errors) {
        if (errors.hasErrors()) {
            Map<String, String> map = new HashMap<>();
            List<FieldError> list = errors.getFieldErrors();
            for (FieldError fieldError : list) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return map;
        }
        return null;
    }

    public long getDateDifference(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
        return diffInDays;
    }
}
