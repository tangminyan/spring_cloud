package eureka.service_hello_world.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tangminyan on 2019/6/17.
 */
@Controller
public class DemoController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/")
    public String getMsg() {
        return "welcome";
    }
}
