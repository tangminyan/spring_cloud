package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tangminyan on 2019/9/7.
 */
@Controller
@RequestMapping(value = "waf-product/IBS")
public class MockController {

    @RequestMapping("/add")
    public ResponseEntity add(Objects jsonObject) {
        Map<String, Object> res = new HashMap<>();
        res.put("errCode", 0);
        res.put("msg", "操作成功");
        return ResponseEntity.ok(res);
    }

}

