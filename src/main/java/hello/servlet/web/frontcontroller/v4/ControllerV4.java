package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {

    /**
     *
     * @param paramMap request.getParameterNames 를 루프를 돌려서 생성한 Map 객체에 키와 값 모두 담는다.
     * @param model jsp 페이지에 전달할 attribute 값들을 model 에 담는다.
     * @return
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
