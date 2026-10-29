package org.sopt.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.sopt.member.domain.Account;

public record MemberResponse(
        @Schema(description = "회원 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "email@gmail.com")
        String email,

        @Schema(description = "닉네임", example = "닉네임")
        String nickname
) {
    public static MemberResponse from(Account account) {
        return new MemberResponse(account.getMember().getId(), account.getEmail(), account.getMember().getNickname());
    }
}
