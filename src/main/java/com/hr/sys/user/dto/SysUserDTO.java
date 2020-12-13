package com.hr.sys.user.dto;

import lombok.Data;

/**
 * @Author fengz
 * @Date 2020/12/7 20:57
 */
@Data
public class SysUserDTO {
    private String account;
    private String password;
    private String name;
    private String role;
}
