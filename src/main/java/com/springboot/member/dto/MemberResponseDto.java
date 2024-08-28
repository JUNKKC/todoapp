package com.springboot.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 회원 정보를 클라이언트에게 응답할 때 사용되는 DTO 클래스입니다.
@Getter  // Lombok을 사용하여 자동으로 getter 메서드를 생성합니다.
@Setter  // Lombok을 사용하여 자동으로 setter 메서드를 생성합니다.
public class MemberResponseDto {

  private long memberId;  // 회원의 고유 ID를 나타냅니다.
  private String email;   // 회원의 이메일 주소를 나타냅니다.
  private String name;    // 회원의 이름을 나타냅니다.
}