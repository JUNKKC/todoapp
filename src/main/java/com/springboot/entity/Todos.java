package com.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Todos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long todoId;
  @Column(nullable = false, length = 20)
  private String title;
  @Column(nullable = false)
  private int todoOrder;
  @Column(nullable = false)
  private boolean completed;
}
