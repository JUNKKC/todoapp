package com.springboot.auth.handler;

import com.google.gson.Gson;
import com.springboot.response.ErrorResponse;
import com.springboot.exception.BusinessLogicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;

        // 예외가 BusinessLogicException 인스턴스인지 확인하여 회원 존재 여부 판단
        if (exception.getCause() instanceof BusinessLogicException) {
            log.error("로그인 실패: 존재하지 않는 회원입니다.");
            errorMessage = "존재하지 않는 회원입니다.";
        } else if (exception instanceof BadCredentialsException) {
            log.error("로그인 실패: 비밀번호가 잘못되었습니다.");
            errorMessage = "비밀번호가 잘못되었습니다.";
        } else {
            log.error("로그인 실패: {}", exception.getMessage());
            errorMessage = "로그인에 실패했습니다. 다시 시도해주세요.";
        }

        // 클라이언트로 오류 응답 전송
        sendErrorResponse(response, errorMessage);
    }

    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        Gson gson = new Gson();

        // ErrorResponse 인스턴스를 생성하기 위해 정적 메서드 사용
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED, errorMessage);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(gson.toJson(errorResponse));
    }
}