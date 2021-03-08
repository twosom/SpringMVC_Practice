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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        String uriPath = "/front-controller/v1/members";
        controllerMap.put(uriPath + "/new-form", new MemberFormControllerV1());
        controllerMap.put(uriPath + "/save", new MemberSaveControllerV1());
        controllerMap.put(uriPath, new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();
        ControllerV1 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(
                    "<html>" +
                    "<head>" +
                            "<title>페이지 오류</title>" +
                            "</head>" +
                            "<body>" +
                            "<div>" +
                            "<h1>지정된 페이지" + requestURI + "를 찾을 수 없습니다. </h1>" +
                            "</div>" +
                            "</body>" +
                    "</html>");
            return;
        }
        controller.process(request, response);
    }
}
