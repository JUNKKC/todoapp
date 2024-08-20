package com.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class TodoPostDto {
  private long id;
  private String title;
  private int todoOrder;
  private boolean completed;
  private LocalDateTime modifiedAt = LocalDateTime.now();
}
