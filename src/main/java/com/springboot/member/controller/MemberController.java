package com.springboot.member.controller;

import com.springboot.member.dto.MemberPatchDto;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.entity.Member;
import com.springboot.member.mapper.MemberMapper;
import com.springboot.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.security.core.Authentication;

@Slf4j
@RestController
@RequestMapping("/members")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

  private final MemberService memberService;
  private final MemberMapper memberMapper;

  public MemberController(MemberService memberService, MemberMapper memberMapper) {
    this.memberService = memberService;
    this.memberMapper = memberMapper;
  }

  // 회원가입
  @PostMapping("/")
  public ResponseEntity<?> createMember(@Valid @RequestBody MemberPostDto memberPostDto) {
    Member member = memberMapper.memberPostDtoToMember(memberPostDto);
    Member response = memberService.createMember(member);
    log.info("회원가입 성공");
    return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(response), HttpStatus.CREATED);
  }

  // 회원정보 수정 (이름 및 비밀번호)
  @PatchMapping("/{id}")
  public ResponseEntity<?> updateMember(@PathVariable("id") long id,
                                        @Valid @RequestBody MemberPatchDto memberPatchDto,
                                        Authentication authentication) {
    // 로그인한 사용자인지 검증
    Member authenticatedMember = memberService.findAuthenticatedMember(authentication);
    if (authenticatedMember.getMemberId() != id) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // DTO에서 전달받은 값을 서비스 레이어로 전달
    Member updatedMember = memberService.updateMember(id, memberPatchDto.getName(),
        memberPatchDto.getOldPassword(),
        memberPatchDto.getNewPassword());
    log.info("회원정보 수정 완료");

    return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(updatedMember), HttpStatus.OK);
  }

  // 회원정보 1인 조회
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") long id) {
    Member member = memberService.findMember(id);
    log.info("조회 완료");
    return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(member), HttpStatus.OK);
  }

  // 회원 전체 조회
  @GetMapping("/")
  public ResponseEntity<?> findAll() {
    log.info("전체 조회 완료");
    return new ResponseEntity<>(memberMapper.membersToMemberResponseDtos(memberService.findAllMembers()), HttpStatus.OK);
  }

  // 회원 탈퇴 - 1인 db에서 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteMember(@PathVariable("id") long id) {
    memberService.deleteMember(id);
    log.info("삭제 완료");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // 회원 전체 삭제
  @DeleteMapping("/")
  public ResponseEntity<?> deleteAll() {
    memberService.deleteAllMembers();
    log.info("전체 삭제 완료");
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // 새로운 엔드포인트 추가: 로그인한 사용자의 정보 가져오기
  @GetMapping("/me")
  public ResponseEntity<?> getCurrentMember(Authentication authentication) {
    Member member = memberService.findAuthenticatedMember(authentication);
    return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(member), HttpStatus.OK);
  }
}