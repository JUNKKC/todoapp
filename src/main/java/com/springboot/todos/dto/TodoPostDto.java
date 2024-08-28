package com.springboot.todos.dto;

import com.springboot.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class TodoPostDto {

  private String title;
  private int todoOrder;
  private boolean completed;
  private LocalDateTime modifiedAt = LocalDateTime.now();
 private Member member;

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }
}
