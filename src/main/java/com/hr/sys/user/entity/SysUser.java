package com.hr.sys.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class SysUser {

  @Id
  private String id;
  private String account;
  private String password;
  private String name;
  private String role;

}
