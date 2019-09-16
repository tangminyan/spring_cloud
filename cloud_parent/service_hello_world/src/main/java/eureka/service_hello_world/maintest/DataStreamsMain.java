package eureka.service_hello_world.maintest;

/**
 * Created by tangminyan on 2019/9/16.
 */
public class DataStreamsMain {
    public static void main(String[] args) {

        DataStreamDemo.writeToFile("G:\\WorkSpace\\JetBrain\\mygithub\\new_project\\spring_cloud\\cloud_parent\\testfile");

        DataStreamDemo.readFromFile("G:\\WorkSpace\\JetBrain\\mygithub\\new_project\\spring_cloud\\cloud_parent\\testfile");
        System.out.println("before 线程: " + Thread.currentThread().getName());
        DataStreamDemo.readWithFuture("tmp.txt");
        DataStreamDemo.readWithCompletionHandler("tmp.txt");
//        System.out.println("主线程: " + Thread.currentThread().getName());

    }

}
