package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.domain.memer.Member;
import hello.servlet.domain.memer.MemberRepository;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberSaveControllerV2 implements ControllerV2 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        Member member = new Member(username, age);

        Member savedMember = memberRepository.save(member);
        request.setAttribute("savedMember", savedMember);
        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
