package com.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class TodoPostDto {
  private String title;
  private int todoOrder;
  private boolean completed;

}
