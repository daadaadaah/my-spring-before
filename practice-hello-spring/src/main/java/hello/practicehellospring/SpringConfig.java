package hello.practicehellospring;

import hello.practicehellospring.repository.MemberRepository;
import hello.practicehellospring.repository.MemoryMemberRepository;
import hello.practicehellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
