package eureka.service_hello_world.service.impl;

import eureka.service_hello_world.service.FutrueTaskDemoService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by tangminyan on 2019/9/11.
 */
@Service
public class FutrueTaskDemoServiceImpl implements FutrueTaskDemoService {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void FutureTaskDemo() {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("ceshi-1-xia");
            return "ceshi";
        });
        executorService.submit(futureTask);
    }
}
