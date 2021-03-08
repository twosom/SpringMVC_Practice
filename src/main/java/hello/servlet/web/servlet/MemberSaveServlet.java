package hello.servlet.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.domain.memer.Member;
import hello.servlet.domain.memer.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "memberSaveServlet", urlPatterns = "/servlet/members/save")
public class MemberSaveServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String age = request.getParameter("age");

        Member member = new Member(username, Integer.parseInt(age));
        memberRepository.save(member);

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter w = response.getWriter();

        w.write("<html>" +
                        "<body>" +
                        "<ul>" +
                        "<li>id=" + member.getId() + "</li>" +
                        "<li>username=" + member.getUsername() + "</li>" +
                        "<li>age=" + member.getAge() + "</li> " +
                        "</ul>"+
                "</body>" +
                        "</html>");
    }
}
