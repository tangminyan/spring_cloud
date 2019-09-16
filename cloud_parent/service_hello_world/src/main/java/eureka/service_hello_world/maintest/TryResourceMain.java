package eureka.service_hello_world.maintest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by tangminyan on 2019/9/16.
 */
public class TryResourceMain {

    public static void main(String[] args) throws IOException {
        System.out.println(readFirstFromFile("service_hello_world/tmp.txt"));

    }

    static String readFirstFromFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }
}
