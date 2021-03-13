package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import org.springframework.mail.javamail.MimeMailMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerV4Servlet", urlPatterns = "/front-controller/v4/*")
public class FrontControllerV4Servlet extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();
    private String commonPath = "/front-controller/v4/";

    public FrontControllerV4Servlet() {
        this.controllerMap.put(commonPath + "members/new-form", new MemberFormControllerV4());
        this.controllerMap.put(commonPath + "members/save", new MemberSaveControllerV4());
        this.controllerMap.put(commonPath + "members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("<html><title>페이지를 찾을 수 없습니다.</title><body>요청하신 페이지 " + requestURI + " 에 대한 페이지를 찾을 수 없습니다.</body></html>");
            return;
        }

        Map<String, String> paramMap = createParam(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);

        view.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
        return view;
    }

    private Map<String, String> createParam(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames()
                .asIterator().forEachRemaining(parameterName -> paramMap.put(parameterName, request.getParameter(parameterName)));
        return paramMap;
    }
}
