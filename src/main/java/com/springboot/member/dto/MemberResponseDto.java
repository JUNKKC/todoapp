package com.springboot.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberResponseDto {
  private long memberId;
  private String email;
  private String name;

}

