package com.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class TodoResponseDto {

  private long id;
  private String title;
  private int todoOrder;
  private boolean completed;
}
