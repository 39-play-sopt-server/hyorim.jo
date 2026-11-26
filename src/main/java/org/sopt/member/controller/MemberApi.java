package org.sopt.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.swagger.ApiSuccessCode;
import org.sopt.member.dto.MemberResponse;
import org.sopt.post.dto.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API", description = "회원과 관련된 API 입니다")
@RequestMapping(path = "/api/v1/members")
public interface MemberApi {
    @Operation(summary = "회원 탈퇴", description = "회원을 삭제합니다.")
    @DeleteMapping
    ResponseEntity<ApiResponse<Void>> deleteMember(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    );

    @Operation(summary = "회원 정보 조회", description = "회원 정볼를 조회합니다.")
    @GetMapping("/me")
    ResponseEntity<ApiResponse<MemberResponse>> me(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    );
}
