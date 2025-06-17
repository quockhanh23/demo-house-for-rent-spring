package com.example.testspringweb.dto;

import com.example.testspringweb.models.House;
import com.example.testspringweb.models.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionalHistoryUser {
    private House house;
    private List<Transactional> transactionalList;
}
