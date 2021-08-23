package com.liang.lagou_minicat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

        while (true){

            Socket socket = serverSocket.accept();

            OutputStream outputStream = socket.getOutputStream();

            String str = "hello world";

            String httpHeader200 = HttpProtocolUtil.getHttpHeader200(str.getBytes().length);


            outputStream.write((httpHeader200+str).getBytes());

            socket.close();

        }



    }




    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
