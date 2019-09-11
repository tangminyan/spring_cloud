package eureka.service_hello_world.controller;

import eureka.service_hello_world.service.PublickeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by tangminyan on 2019/6/17.
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Value("${server.port}")
    private String port;

    @Autowired
    private PublickeyService publickeyService;

    @RequestMapping("/welcome")
    public String getMsg() {
        return "welcome";
    }

    @RequestMapping("/publickey")
    public String getPublicKey() {
        try {
            Map<String, Object> map = publickeyService.initKey();
//            System.out.println(publickeyService.getPublicKey(map));
//            System.out.println(publickeyService.getPrivateKey(map));
            return publickeyService.getPublicKey(map) + "/n" + publickeyService.getPrivateKey(map);
        } catch (Exception e) {
            return "error";
        }
    }


}
