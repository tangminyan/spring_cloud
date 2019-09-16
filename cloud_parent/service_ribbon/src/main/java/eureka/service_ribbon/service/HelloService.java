package eureka.service_ribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by tangminyan on 2019/6/17.
 */
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "serviceFailure")
    public String getHelloContent() {
        return restTemplate.getForObject("http://EUREKA-CLIENT-SERVICE-PROVIDER/", String.class);
    }

    public String serviceFailure() {
        return "hello world service is not available !";
    }
}
