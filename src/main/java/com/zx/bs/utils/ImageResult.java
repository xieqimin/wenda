package com.zx.bs.utils;

import lombok.Data;

@Data
public class ImageResult {
    private Integer errno;
    /** 具体的内容. */
    private String [] data;
}
