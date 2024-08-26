package com.springboot.member.controller;

import com.springboot.member.dto.MemberPatchDto;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.entity.Member;
import com.springboot.member.mapper.MemberMapper;
import com.springboot.member.service.MemberService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/members")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

  private  final MemberService memberService;
    private  final MemberMapper mapper;
  private final MemberMapper memberMapper;

  public MemberController(MemberService memberService, MemberMapper mapper, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    this.memberMapper = memberMapper;
  }
    @PostMapping("/")
    public ResponseEntity createMember(@Valid  @RequestBody MemberPostDto memberPostDto) {
      Member member = mapper.memberPostDtoToMember(memberPostDto);
      Member response = memberService.createMember(member);
      log.info("회원가입 성공");

        return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(response), HttpStatus.CREATED);

    }
    @PatchMapping("/{id}")
    public ResponseEntity updateMember(@PathVariable("id") long id, @Valid @RequestBody MemberPatchDto memberPatchDto) {
      memberPatchDto.setMemberId(id);
        Member member = memberService.updateMember(mapper.memberPatchDtoToMember(memberPatchDto));
        log.info("수정 완료");
        return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(member), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") long id) {
        Member member = memberService.findMember(id);
        log.info("조회 완료");
        return new ResponseEntity<>(memberMapper.memberToMemberResponseDto(member), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity findAll() {
        log.info("전체 조회 완료");
        return new ResponseEntity<>(memberMapper.membersToMemberResponseDtos(memberService.findAllMembers()), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMember(@PathVariable("id") long id) {
        memberService.deleteMember(id);
        log.info("삭제 완료");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/")
    public ResponseEntity deleteAll() {
        memberService.deleteAllMembers();
        log.info("전체 삭제 완료");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
