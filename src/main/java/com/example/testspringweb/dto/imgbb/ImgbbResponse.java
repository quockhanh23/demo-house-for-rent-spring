package com.example.testspringweb.dto.imgbb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImgbbResponse {
    private Data data;
    private boolean success;
    private int status;
}
