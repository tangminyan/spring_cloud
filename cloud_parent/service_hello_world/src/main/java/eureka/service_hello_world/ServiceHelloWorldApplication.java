package eureka.service_hello_world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceHelloWorldApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHelloWorldApplication.class, args);
    }

}
