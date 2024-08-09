package com.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoPatchDto {
  private long id;
  private String title;
  private int todoOrder;
  private boolean completed;

  public void setId(long id) {
    this.id = id;
  }

}
