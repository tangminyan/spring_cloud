package eureka.service_hello_world.maintest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * Created by tangminyan on 2019/9/16.
 */
public class DataStreamDemo {
    public static void writeToFile(String filename){

        double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
        int[] units = { 12, 8, 13, 29, 50 };
        String[] descs = {
                "Java T-shirt",
                "Java Mug",
                "Duke Juggling Dolls",
                "Java Pin",
                "Java Key Chain"
        };
        try(DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(filename)))){
            for (int i = 0; i < prices.length; i ++) {
                out.writeDouble(prices[i]);
                out.writeInt(units[i]);
                out.writeUTF(descs[i]);
            }

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void readFromFile(String filename){
        double price;
        int unit;
        String desc;
        double total = 0.0;
        try(DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(filename)))){
            while (true) {
                price = in.readDouble();
                unit = in.readInt();
                desc = in.readUTF();
                System.out.format("You ordered %d" + " units of %s at $%.2f%n", unit, desc, price);
                total += unit * price;
            }

        }catch(EOFException e){

            System.out.format("All data is read in, total price is: $%.2f%n", total);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 异步操作
     * @param uri
     */
    public static void readWithFuture(String uri) {
        try {
            for (int i = 0; i < 2; i++) {
                AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(uri), StandardOpenOption.READ);
                System.out.println("当前线程: " + Thread.currentThread().getName());
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                long position = 0;

                Future<Integer> operation = channel.read(buffer, position);
                while (!operation.isDone());
                buffer.flip();
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
//            System.out.println(new String(data));
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readWithCompletionHandler(String uri) {
        try {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(uri), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024); //创建缓冲区
            long position = 0;
            channel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("Read Done");
                    System.out.println("result = " + result);
                    buffer.flip();
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    System.out.println(new String(data));
                    buffer.clear();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("Read failed");
                    exc.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





















