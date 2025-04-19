package com.example.testspringweb.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlUnCheck {
    public static final String URL_HOUSE_LIST = "/api/houses/getAllPage";
    public static final String URL_HOUSE_LIST_BY_ADDRESS = "/api/houses/getAllHouseByAddress";
    public static final String URL_HOUSE_DETAIL = "/api/houses/getDetailHouse";
    public static final String GET_ALL_WARD_BY_DISTRICT_AND_COUNT = "/api/houses/getAllWardByDistrictAndCount";
    public static final String GET_ALL_DISTRICT_AND_COUNT = "/api/houses/getAllDistrictAndCount";
    public static final String TOP_MOST_EXPENSIVE = "/api/houses/topMostExpensive";
}
