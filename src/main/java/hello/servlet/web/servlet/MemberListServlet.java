package hello.servlet.web.servlet;

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

@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Member> members = memberRepository.findAll();

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter w = response.getWriter();

        w.write("<html>" +
                "<head>" +
                "<meta charset=\"utf-8\"/>" +
                "<title>Title</title>" +
                "</head>" +
                "<body>" +
                "<a href=\"index.html\">메인</a>" +
                "<table>" +
                "<thead>" +
                "<th>id</th> <th>username</th> <th>age</th>" +
                "</thead> " +
                "<tbody>");

        members.forEach(member -> {
            w.write("<tr>");
            w.write("<td>" + member.getId() + "</td>");
            w.write("<td>" + member.getUsername() + "</td>");
            w.write("<td>" + member.getAge() + "</td>");
            w.write("</tr>");
        });
        w.write("</tbody> " +
                "</table>" +
                "</body>" +
                "</html>");
    }
}
