package com.springboot.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

// 회원 가입 요청 시 사용되는 DTO 클래스입니다.
@Getter  // Lombok을 사용하여 자동으로 getter 메서드를 생성합니다.
public class MemberPostDto {

  @NotBlank  // 값이 비어있지 않아야 함을 검증합니다.
  @Email  // 유효한 이메일 형식인지 검증합니다.
  private String email;

  @NotBlank  // 값이 비어있지 않아야 함을 검증합니다.
  private String password;

  @NotBlank(message = "이름은 공백이 아니어야 합니다.")  // 값이 비어있지 않아야 하며, 공백일 경우 메시지를 출력합니다.
  private String name;
}