package eureka.config_client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tangminyan on 2019/6/17.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Value("${user.userName}")
    private String userName;

    @Value("${user.sex}")
    private String sex;
    @Value("${user.age}")
    private Integer age;

    @RequestMapping(value = "/info")
    public String getUserInfo(){
        return "userName:"+userName+" sex:"+sex+" age:"+age;
    }
}

