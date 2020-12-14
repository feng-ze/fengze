package com.hr.sys.user.dto;

import lombok.Data;

/**
 * @Author fengz
 * @Date 2020/12/14 11:28
 */
@Data
public class Message {
    private String code;
    private String message;

    public Message(String s, String s1) {
        this.code = s;
        this.message = s1;
    }

    public Message(String message) {
        this.code = "0";
        this.message = message;
    }
}
