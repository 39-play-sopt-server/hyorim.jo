package org.sopt.member.service;

import org.sopt.global.exception.GeneralException;
import org.sopt.member.code.MemberErrorCode;
import org.sopt.member.domain.Account;
import org.sopt.member.domain.Member;
import org.sopt.member.dto.CreateMemberRequest;
import org.sopt.member.dto.MemberResponse;
import org.sopt.member.repository.AccountRepository;
import org.sopt.member.repository.MemberRepository;
import org.sopt.member.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final ProfileRepository profileRepository;

    public MemberService(MemberRepository memberRepository, AccountRepository accountRepository, ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.accountRepository = accountRepository;
        this.profileRepository = profileRepository;
    }

    // 회원 생성
    @Transactional
    public MemberResponse createMember(CreateMemberRequest request) {
        if (request.email() == null || request.email().isBlank()) {
            throw new GeneralException(MemberErrorCode.MEMBER_EMAIL_EMPTY);
        }
        if (request.password() == null || request.password().isEmpty()) {
            throw new GeneralException(MemberErrorCode.MEMBER_PASSWORD_EMPTY);
        }
        if (request.nickname() == null || request.nickname().isBlank()) {
            throw new GeneralException(MemberErrorCode.MEMBER_NICKNAME_EMPTY);
        }
        if (accountRepository.existsByEmail(request.email())) {
            throw new GeneralException(MemberErrorCode.MEMBER_DUPLICATE_EMAIL);
        }

        Member member = memberRepository.save(new Member(request.nickname()));
        Account account = accountRepository.save(new Account(member, request.email(), request.password()));

        return MemberResponse.from(account);
    }

    // 회원 삭제
    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (profileRepository.existsByMember(member)) {
            throw new GeneralException(MemberErrorCode.MEMBER_HAS_PROFILES);
        }

        accountRepository.findByMember(member).ifPresent(accountRepository::delete);
        memberRepository.delete(member);
    }
}
