package com.springboot.member.mapper;

import com.springboot.member.dto.MemberPatchDto;
import com.springboot.member.dto.MemberPostDto;
import com.springboot.member.dto.MemberResponseDto;
import com.springboot.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

// MapStruct를 사용하여 DTO와 엔티티 간의 매핑을 자동으로 처리하는 인터페이스를 정의합니다.
// 이 인터페이스는 Spring의 컴포넌트로 관리되며, unmappedTargetPolicy 설정을 통해
// 매핑되지 않은 필드를 무시하도록 지정합니다.
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

  // MemberPostDto를 Member 엔티티로 변환하는 메서드입니다.
  // 회원가입 시 사용자가 입력한 데이터를 데이터베이스에 저장하기 위한 Member 엔티티로 매핑합니다.
  Member memberPostDtoToMember(MemberPostDto memberPostDto);

  // MemberPatchDto를 Member 엔티티로 변환하는 메서드입니다.
  // 회원 정보 수정 시 사용자가 입력한 데이터를 데이터베이스에 반영하기 위한 Member 엔티티로 매핑합니다.
  Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);

  // Member 엔티티를 MemberResponseDto로 변환하는 메서드입니다.
  // 데이터베이스에서 가져온 회원 정보를 클라이언트에 반환하기 위한 DTO로 매핑합니다.
  MemberResponseDto memberToMemberResponseDto(Member member);

  // 여러 Member 엔티티를 MemberResponseDto 리스트로 변환하는 메서드입니다.
  // 데이터베이스에서 가져온 다수의 회원 정보를 클라이언트에 반환하기 위한 DTO 리스트로 매핑합니다.
  List<MemberResponseDto> membersToMemberResponseDtos(List<Member> members);
}