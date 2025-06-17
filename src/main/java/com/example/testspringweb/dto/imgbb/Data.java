package com.example.testspringweb.dto.imgbb;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
    private String id;
    private String title;
    private String url_viewer;
    private String url;
    private String display_url;
    private int width;
    private int height;
    private int size;
    private long time;
    private int expiration;
    private Image image;
    private Thumb thumb;
    private Medium medium;
    private String delete_url;
}
