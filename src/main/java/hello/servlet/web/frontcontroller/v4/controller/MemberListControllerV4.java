package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.domain.memer.Member;
import hello.servlet.domain.memer.MemberRepository;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        System.out.println("MemberListControllerV4.process");
        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        return "members";
    }
}
