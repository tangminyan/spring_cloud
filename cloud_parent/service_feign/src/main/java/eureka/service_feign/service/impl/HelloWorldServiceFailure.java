package eureka.service_feign.service.impl;

import eureka.service_feign.service.HelloWorldService;
import org.springframework.stereotype.Component;

/**
 * Created by tangminyan on 2019/6/18.
 */
@Component
public class HelloWorldServiceFailure implements HelloWorldService {
    @Override
    public String sayHello() {
        System.out.println("hello world service is not available !");
        return "hello world service is not available !";
    }
}
