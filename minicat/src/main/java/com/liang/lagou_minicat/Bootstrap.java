package com.liang.lagou_minicat;

import com.liang.minicat.servlet.api.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Bootstrap {

    private int port = 8080;

    public static void main(String[] args) {

        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    //启动初始化过程
    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("minicat----start");


        //5.0 加载webapps目录
        ConfigParser configParser = new ConfigParser();
        try {
            configParser.init();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //定义线程池
        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler
        );

        //使用线程池
        while (true){
            System.out.println("使用线程池");
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket,configParser.getServletMap());
            threadPoolExecutor.execute(requestProcessor);
        }


    }





    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
