package com.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoPatchDto {
  private long id;
  private String title;
  private int todoOrder;
  private boolean completed;
  private LocalDateTime modifiedAt = LocalDateTime.now();

  public void setId(long id) {
    this.id = id;
  }

}
