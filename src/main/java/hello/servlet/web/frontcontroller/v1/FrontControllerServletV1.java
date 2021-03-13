package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * urlPatterns 의 /front-controller/v1/* 은 /front-controller/v1/ 에서의 모든 요청을 받는다는 의미.
 */
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();
    private final String commonPath = "/front-controller/v1/";

    public FrontControllerServletV1() {
        controllerMap.put(commonPath + "members/new-form", new MemberFormControllerV1());
        controllerMap.put(commonPath + "members/save", new MemberSaveControllerV1());
        controllerMap.put(commonPath + "members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.err.println("requestURI = " + requestURI);
        ControllerV1 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("<html><title>페이지를 찾을 수 없습니다.</title><body>요청하신 페이지 " + requestURI + " 에 대한 페이지를 찾을 수 없습니다.</body></html>");
            return;
        }
        controller.process(request, response);
    }
}
