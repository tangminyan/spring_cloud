package eureka.service_hello_world.maintest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by tangminyan on 2019/9/11.
 */
public class FutureTaskMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        FutureTaskMain taskMain = new FutureTaskMain();
        Integer totalResult = 0;
        for (int i = 2; i < 8; i++) {
            FutureTask<Integer> futureTask = new FutureTask<>(taskMain.new ComputeTask("NO."+i));
            executorService.submit(futureTask);
            System.out.println(futureTask.get());
            totalResult += futureTask.get();
        }
        System.out.println("所有计算任务提交完毕, 主线程接着干其他事情！");
        executorService.shutdown();
        System.out.println("多任务计算后的总结果是:" + totalResult);
    }

    private class ComputeTask implements Callable {

        private Integer result = 0;
        private String taskName = "";

        public ComputeTask(String initTask) {
            this.result = 0;
            this.taskName = initTask;
            System.out.println("生成子线程计算任务: "+taskName);
        }

        @Override
        public Object call() throws Exception {
            for (int i = 0; i < 17; i++) {
                result += i;
            }

            Thread.sleep(5000);
            System.out.println("子线程计算任务: "+taskName+" 执行完成!");
            return result;
        }
    }
}
