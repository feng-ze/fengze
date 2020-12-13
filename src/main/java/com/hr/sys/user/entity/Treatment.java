package com.hr.sys.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "treatment")
public class Treatment {

  @Id
  private String id;
  private String name;
  private String worknumber;
  private String salary;
  private String traffic;
  private String heating;
  private String housing;
  private String dinner;
  private String end;
  private String med;
  private String unem;
  private String acc;
  private String tax;
  private String wages;

}
