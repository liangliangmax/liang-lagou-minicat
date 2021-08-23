package com.liang.lagou_minicat;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Request {

    private InputStream inputStream;

    private String method;

    private String url;

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        int count = 0;

        while (count == 0){

            count = inputStream.available();
        }

        byte[] bytes = new byte[count];

        inputStream.read(bytes);

        String str = new String(bytes);

        String firstLineStr = str.split("\\n")[0]; // GET / HTTP/1.1

        String[] strings = firstLineStr.split(" ");

        this.method = strings[0];

        this.url = strings[1];


        System.out.println("url==>"+this.url);

        System.out.println("method===>"+this.method);

    }


    public Request(){}

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
