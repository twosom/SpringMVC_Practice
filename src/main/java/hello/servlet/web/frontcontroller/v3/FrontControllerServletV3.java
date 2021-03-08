package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        String uriPath = "/front-controller/v3/members";
        controllerMap.put(uriPath + "/new-form", new MemberFormControllerV3());
        controllerMap.put(uriPath + "/save", new MemberSaveControllerV3());
        controllerMap.put(uriPath, new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
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

        //paramMap

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        MyView view = viewResolver(mv.getViewName());

        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
