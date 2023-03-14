package hello.practicehellospring.repository;

import hello.practicehellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    void update(Long id, Member member);
    void deleteById(Long id);
}
