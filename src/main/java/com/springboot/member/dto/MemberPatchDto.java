package com.springboot.member.dto;

import com.springboot.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

// 회원 정보 수정 요청 시 사용되는 DTO 클래스입니다.
@Getter  // Lombok을 사용하여 자동으로 getter 메서드를 생성합니다.
@Setter  // Lombok을 사용하여 자동으로 setter 메서드를 생성합니다.
public class MemberPatchDto {

  private long memberId;  // 수정할 회원의 ID를 나타냅니다.

  @NotSpace(message = "회원 이름은 공백이 아니어야 합니다")  // 공백이 아닌 값만 허용하도록 커스텀 검증 어노테이션을 사용합니다.
  private String name;

  // memberId를 설정하는 커스텀 setter 메서드입니다.
  public void setMemberId(long memberId) {
    this.memberId = memberId;
  }
}