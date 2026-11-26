package org.sopt.member.service;

import org.sopt.global.exception.GeneralException;
import org.sopt.member.code.MemberErrorCode;
import org.sopt.member.domain.Account;
import org.sopt.member.domain.Member;
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

    public MemberResponse getMember(Long memberId) {
        Account account = accountRepository.findByMemberId(memberId)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberResponse.from(account);
    }
}
