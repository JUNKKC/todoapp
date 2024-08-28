package com.springboot.member.mapper;

import com.springboot.member.dto.MemberPatchDto;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.dto.MemberResponseDto;
import com.springboot.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
Member memberPostDtoToMember(MemberPostDto memberPostDto);
Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
MemberResponseDto memberToMemberResponseDto(Member member);
List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);
}
