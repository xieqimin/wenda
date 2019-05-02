package com.zx.bs.utils;

public class ImageResultUtil {
    public static ImageResult success(String []object) {
        ImageResult result = new ImageResult();
        result.setErrno(0);
        result.setData(object);
        return result;
    }
    public static ImageResult success() {
        return success(null);
    }
}
