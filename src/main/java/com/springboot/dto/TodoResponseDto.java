package com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TodoResponseDto {

  private long todosId;
  private String todosTitle;
  private int todoOrder;
  private boolean Completed;
}
