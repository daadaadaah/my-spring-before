package hello.practicehellospring.controller;

import hello.practicehellospring.domain.MemberForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import hello.practicehellospring.domain.Member;
import hello.practicehellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ResponseEntity<Member> create(@RequestBody MemberForm memberForm) throws IOException {
        Member member = new Member();
        member.setName(memberForm.getName());

        memberService.join(member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<Member> getMember(@PathVariable("memberId") Long memberId) throws IOException {
        Member result = memberService.findOne(memberId).get();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/member/{memberId}")
    public ResponseEntity<Member> updateMember(@PathVariable("memberId") Long memberId, @RequestBody String name) throws IOException {
        Member newMember = new Member();
        newMember.setId(memberId);
        newMember.setName(name);

        Member result = memberService.updateMember(memberId, newMember);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<Member> removeMember(@PathVariable("memberId") Long memberId) throws IOException {
        Member member = new Member();
        member.setName("test");

        memberService.join(member);

        Member result = memberService.withdraw(memberId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
