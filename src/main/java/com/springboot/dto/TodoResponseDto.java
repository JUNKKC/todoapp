package com.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TodoResponseDto {

  private long id;
  private String title;
  private int todoOrder;
  private boolean completed;
}
