package org.sopt.member.controller;

import org.sopt.global.response.ApiResponse;
import org.sopt.member.dto.CreateMemberRequest;
import org.sopt.member.dto.MemberResponse;
import org.sopt.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController implements MemberApi {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(CreateMemberRequest request) {
        return ApiResponse.created(memberService.createMember(request));
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> deleteMember(Long memberId) {
        memberService.deleteMember(memberId);
        return ApiResponse.ok();
    }
}
