package com.example.mydal.enums;

/**
 * @Author:wb-cgm503374
 * @Description
 * @Date:Created in 2020/11/30 12:10 上午
 */

public enum GoodsStatusEnum {
    NORMAL("10","正常"),
    SOLD_OUT("90","售罄"),
    DOWN_SHELVES("91","下架")
    ;


    private String code;
    private String desc;

    GoodsStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
}
