package org.sopt.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateMemberRequest(
        @Schema(description = "이메일", example = "email@gmail.com")
        String email,

        @Schema(description = "비밀번호", example = "password")
        String password,

        @Schema(description = "닉네임", example = "닉네임")
        String nickname
) {

}
