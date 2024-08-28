package com.springboot.member.dto;

import com.springboot.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPatchDto {
  private long memberId;
  @NotSpace(message = "회원 이름은 공백이 아니어야 합니다")
  private String name;

  public void setMemberId(long memberId) {
    this.memberId = memberId;
  }
}
