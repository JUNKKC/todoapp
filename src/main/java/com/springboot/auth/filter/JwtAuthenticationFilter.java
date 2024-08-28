package com.springboot.auth.filter;

import com.springboot.auth.dto.LoginDto;
import com.springboot.auth.dto.LoginResponseDto; // LoginResponseDto를 import
import com.springboot.auth.jwt.JwtTokenizer;
import com.springboot.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {  // (1)
  private final AuthenticationManager authenticationManager;
  private final JwtTokenizer jwtTokenizer;

  // (2)
  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenizer jwtTokenizer) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenizer = jwtTokenizer;
  }

  // (3)
  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

    ObjectMapper objectMapper = new ObjectMapper();    // (3-1)
    LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class); // (3-2)

    // (3-3)
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

    return authenticationManager.authenticate(authenticationToken);  // (3-4)
  }

  // (4)
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws ServletException, IOException {
    Member member = (Member) authResult.getPrincipal();  // (4-1)

    String accessToken = delegateAccessToken(member);   // (4-2)
    String refreshToken = delegateRefreshToken(member); // (4-3)

    // 응답 DTO 생성
    LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, refreshToken);

    // ObjectMapper를 사용하여 DTO를 JSON 형식으로 변환
    ObjectMapper objectMapper = new ObjectMapper();
    String responseBody = objectMapper.writeValueAsString(loginResponseDto);

    // JSON 응답 설정
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(responseBody);
  }

  // (5)
  private String delegateAccessToken(Member member) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", member.getEmail());
    claims.put("roles", member.getRoles());

    String subject = member.getEmail();
    Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

    String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

    String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

    return accessToken;
  }

  // (6)
  private String delegateRefreshToken(Member member) {
    String subject = member.getEmail();
    Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
    String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

    String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

    return refreshToken;
  }
}