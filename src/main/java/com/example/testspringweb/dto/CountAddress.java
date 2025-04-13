package com.example.testspringweb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CountAddress {
    private String ward;
    private long numberCount;
}
