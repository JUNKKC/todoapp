package com.springboot.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
public class Todos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(nullable = false, length = 20)
  @NotBlank(message = "제목을 입력해 주세요")
  private String title;
  @Column(nullable = false)
  private int todoOrder = 0;
  @Column(nullable = false)
  private boolean completed;
@Column(nullable = false)
  private LocalDateTime modifiedAt = LocalDateTime.now();



}
