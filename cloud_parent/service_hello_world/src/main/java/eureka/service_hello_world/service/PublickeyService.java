package eureka.service_hello_world.service;

import java.util.Map;

/**
 * Created by tangminyan on 2019/9/3.
 */
public interface PublickeyService {

    Map<String, Object> initKey() throws Exception;

    String getPublicKey(Map<String, Object> keyMap);

    String getPrivateKey(Map<String, Object> keyMap);
}
