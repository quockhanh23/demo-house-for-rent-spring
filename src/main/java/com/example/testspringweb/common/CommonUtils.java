package com.example.testspringweb.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static ObjectMapper intObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    public static List<String> checkDateRange(String date1, String date2) {
        if (StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)) return List.of();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate = LocalDate.parse(date1, formatter);
        LocalDate endDate = LocalDate.parse(date2, formatter);

        List<String> allDates = new ArrayList<>();

        while (!startDate.isAfter(endDate)) {
            allDates.add(startDate.format(formatter));
            startDate = startDate.plusDays(1);
        }
        return allDates;
    }
}
