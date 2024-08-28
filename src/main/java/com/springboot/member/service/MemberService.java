package com.springboot.member.service;

import com.springboot.auth.utils.JwtAuthorityUtils;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

  private final PasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;
  private final JwtAuthorityUtils authorityUtils;

  public MemberService(MemberRepository memberRepository, JwtAuthorityUtils authorityUtils,
                       PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.memberRepository = memberRepository;
    this.authorityUtils = authorityUtils;
  }
  // 회원가입
  public Member createMember(Member member) {
      // 이메일 중복 확인
        verifyExistsEmail(member.getEmail());
      // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
      // 권한 생성
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);
       // 회원 저장
        return memberRepository.save(member);
  }
  // 회원정보 수정
  public Member updateMember(Member member) {
    // 회원 조회
    Member findMember = findVerifiedMember(member.getMemberId());
    // 이름 수정
    Optional.ofNullable(member.getName())
        .ifPresent(findMember::setName);
    // 비밀번호 수정
    Optional.ofNullable(member.getPassword())
        .ifPresent(findMember::setPassword);
    // 회원 저장
    return memberRepository.save(findMember);
  }
  // 회원정보 1인 조회
  public Member findMember(long memberId) {
    return findVerifiedMember(memberId);
  }
  // 회원 전체 조회
  public List<Member> findAllMembers() {
    return memberRepository.findAll();
  }
  // 회원 1인 삭제
  public void deleteMember(long memberId) {
    memberRepository.deleteById(memberId);
  }
  // 회원 전체 삭제
  public void deleteAllMembers() {
    memberRepository.deleteAll();
  }
  // 회원 1인 조회
  public Member findVerifiedMember(long id) {
    Optional<Member> optionalMember = memberRepository.findById(id);
    return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }
  // 이메일 중복 확인
  private void verifyExistsEmail(String email) {
    Optional<Member> member = memberRepository.findByEmail(email);
    if (member.isPresent())
      throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
  }
  // 로그인한 사용자의 정보 가져오기
  @Transactional(readOnly = true)
  public Member findAuthenticatedMember(Authentication authentication) {
    // 인증된 사용자의 이메일을 가져옴
    String username = authentication.getName();
    // 이메일로 회원 조회
    return memberRepository.findByEmail(username)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }
}

