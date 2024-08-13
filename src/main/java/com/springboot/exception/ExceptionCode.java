package com.springboot.exception;

import lombok.Getter;

public enum ExceptionCode {
    TODO_NOT_FOUND(404,"존재하지 않는 일정입니다");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
      this.status = status;
      this.message = message;
    }
}
