package hello.core.web;

import hello.core.common.MyLogger;
import hello.core.logodemo.LogDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger; // 가짜 myLogger 임

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL); // 이때, 진짜 myLogger를 찾아서 동작함

        System.out.println("myLogger = " + myLogger.getClass()); // class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$20b23070 -> 가짜 myLogger

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
