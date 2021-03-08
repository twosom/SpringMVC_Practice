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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        String uriPath = "/front-controller/v2/members";
        controllerMap.put(uriPath + "/new-form", new MemberFormControllerV2());
        controllerMap.put(uriPath + "/save", new MemberSaveControllerV2());
        controllerMap.put(uriPath, new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV2 controller = controllerMap.get(requestURI);
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
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
