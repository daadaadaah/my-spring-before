package hello.practicehellospring.service;

import hello.practicehellospring.domain.Member;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring1");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void duplicateJoin() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        // when
        memberService.join(member1);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
        List<Member> members = memberService.findMembers();

        // given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring2");

        memberService.join(member1);
        memberService.join(member2);

        // when
        List<Member> newMembers = memberService.findMembers();

        // then
        Assertions.assertThat(newMembers.size() - members.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring2");

        memberService.join(member1);
        memberService.join(member2);

        // when
        Member result = memberService.findOne(member1.getId()).get();

        // then
        Assertions.assertThat(result.getName()).isEqualTo(member1.getName());
    }

    @Test
    void updateMember() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        memberService.join(member1);

        Member member2 = new Member();
        member2.setId(member1.getId());
        member2.setName("spring2");

        memberService.updateMember(member1.getId(), member2);

        // when
        Member result = memberService.findOne(member1.getId()).get();

        // then
        Assertions.assertThat(result.getName()).isEqualTo(member2.getName());
    }

    @Test
    void withdraw() {
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        memberService.join(member1);

        memberService.withdraw(member1.getId());

        // when
        Optional<Member> result = memberService.findOne(member1.getId());

        // then
        Assertions.assertThat(result.isPresent()).isFalse();
    }
}
