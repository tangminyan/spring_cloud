package eureka.service_hello_world.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tangminyan on 2019/6/17.
 */
@RestController
public class DemoController {
    @Value("${server.port}")
    private String port;

    @RequestMapping("/")
    public String getMsg() {
        return "Say hello port is: " + port;

    }
}