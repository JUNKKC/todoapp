package com.springboot.exception;

import lombok.Getter;

public enum ExceptionCode {
    TODO_NOT_FOUND(404,"존재하지 않는 일정입니다"),
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다"),
    MEMBER_EXISTS(409, "회원이 이미 존재합니다"),
    CANNOT_CHANGE_ORDER(403, "순서를 변경할 수 없습니다"),
    NOT_IMPLEMENTATION(501, "구현되지 않았습니다"),
    INVALID_MEMBER_STATUS(400, "유효하지 않은 회원 상태입니다"),
   EMAIL_EXISTS(409, "이미 가입된 이메일입니다.");



    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
      this.status = status;
      this.message = message;
    }
}
