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

  public Member createMember(Member member) {
    verifyExistsEmail(member.getEmail());

    String encryptedPassword = passwordEncoder.encode(member.getPassword());
    member.setPassword(encryptedPassword);

    List<String> roles = authorityUtils.createRoles(member.getEmail());
    member.setRoles(roles);

    return memberRepository.save(member);
  }

  public Member updateMember(Member member) {
    Member findMember = findVerifiedMember(member.getMemberId());
    Optional.ofNullable(member.getName())
        .ifPresent(findMember::setName);
    Optional.ofNullable(member.getPassword())
        .ifPresent(findMember::setPassword);
    return memberRepository.save(findMember);
  }

  public Member findMember(long memberId) {
    return findVerifiedMember(memberId);
  }

  public List<Member> findAllMembers() {
    return memberRepository.findAll();
  }

  public void deleteMember(long memberId) {
    memberRepository.deleteById(memberId);
  }

  public void deleteAllMembers() {
    memberRepository.deleteAll();
  }

  public Member findVerifiedMember(long id) {
    Optional<Member> optionalMember = memberRepository.findById(id);
    return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }

  private void verifyExistsEmail(String email) {
    Optional<Member> member = memberRepository.findByEmail(email);
    if (member.isPresent())
      throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
  }

  @Transactional(readOnly = true)
  public Member findAuthenticatedMember(Authentication authentication) {
    String username = authentication.getName(); // 인증된 사용자의 이메일을 가져옴
    return memberRepository.findByEmail(username)
        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
  }
}

