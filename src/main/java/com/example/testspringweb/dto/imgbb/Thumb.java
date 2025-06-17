package com.example.testspringweb.dto.imgbb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Thumb {
    private String filename;
    private String name;
    private String mime;
    private String extension;
    private String url;
}
