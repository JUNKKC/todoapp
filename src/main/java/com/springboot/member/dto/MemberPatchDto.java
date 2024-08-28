package com.springboot.member.dto;

import com.springboot.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

// 회원 정보 수정 요청 시 사용되는 DTO 클래스입니다.
@Getter  // Lombok을 사용하여 자동으로 getter 메서드를 생성합니다.
@Setter  // Lombok을 사용하여 자동으로 setter 메서드를 생성합니다.
public class MemberPatchDto {

  private long memberId;  // 수정할 회원의 ID를 나타냅니다.

  @NotSpace(message = "회원 이름은 공백이 아니어야 합니다")  // 공백이 아닌 값만 허용하도록 커스텀 검증 어노테이션을 사용합니다.
  private String name;

  @NotBlank(message = "비밀번호는 공백일 수 없습니다")
  private String oldPassword;  // 기존 비밀번호 확인을 위해 추가

  @NotBlank(message = "새 비밀번호는 공백일 수 없습니다")
  private String newPassword;  // 새 비밀번호

  // memberId는 직접 설정하지 않고, setMemberId 메서드를 통해 설정합니다.
  public void setMemberId(long memberId) {
    this.memberId = memberId;
  }
}