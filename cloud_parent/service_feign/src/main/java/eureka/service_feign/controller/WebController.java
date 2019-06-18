package eureka.service_feign.controller;

import eureka.service_feign.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tangminyan on 2019/6/18.
 */
@RestController
public class WebController {
    @Autowired
    HelloWorldService helloWorldService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return helloWorldService.sayHello();
    }
}
