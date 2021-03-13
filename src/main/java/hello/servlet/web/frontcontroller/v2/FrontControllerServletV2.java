package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();
    String commonPath = "/front-controller/v2/";

    public FrontControllerServletV2() {
        controllerMap.put(commonPath + "members/new-form", new MemberFormControllerV2());
        controllerMap.put(commonPath + "members/save", new MemberSaveControllerV2());
        controllerMap.put(commonPath + "members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        System.err.println("requestURI = " + requestURI);
        ControllerV2 controller = controllerMap.get(requestURI);


        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("<html><title>페이지를 찾을 수 없습니다.</title><body>요청하신 페이지 " + requestURI + " 에 대한 페이지를 찾을 수 없습니다.</body></html>");
            return;
        }
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
