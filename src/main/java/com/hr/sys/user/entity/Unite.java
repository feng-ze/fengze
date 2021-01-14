package com.hr.sys.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "unite")
public class Unite {

  @Id
  private String id;
  private String userId;
  private String treatId;

}
