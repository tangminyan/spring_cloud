package eureka.service_feign.service;

import eureka.service_feign.service.impl.HelloWorldServiceFailure;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by tangminyan on 2019/6/18.
 */
@FeignClient(value = "EUREKA-CLIENT-SERVICE-PROVIDER", fallback = HelloWorldServiceFailure.class)
public interface HelloWorldService {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String sayHello();
}
