package com.hr.sys.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

  @Id
  private String id;
  private String worknunber;
  private String name;
  private String sex;
  private String phone;
  private String birthday;
  private String address;
  private String school;
  private String major;
  private String education;
  private String marriage;
  private String worktime;
  private String platform;
  private String card;
  private String idcard;

}
