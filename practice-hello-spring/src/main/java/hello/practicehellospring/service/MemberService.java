package hello.practicehellospring.service;

import hello.practicehellospring.domain.Member;
import hello.practicehellospring.repository.MemberRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * */
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증

        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository
                .findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 회원 정보 수정
     */
    public void updateMember(Long memberId, Member member) {
        memberRepository.save(member); // 스프링 데이터 JPA의 인터페이스 맞춰주기 위해 사용
    }

    /**
     * 회원 탈퇴
     */
    public void withdraw(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}