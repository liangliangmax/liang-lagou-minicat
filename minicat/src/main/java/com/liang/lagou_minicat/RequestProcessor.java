package com.liang.lagou_minicat;

import com.liang.minicat.servlet.api.HttpServlet;
import com.liang.minicat.servlet.api.Request;
import com.liang.minicat.servlet.api.Response;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor implements Runnable {

    private Socket socket;

    private Map<String, HttpServlet> servletMap ;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }


    @Override
    public void run() {

        try {
            InputStream inputStream = socket.getInputStream();

            //封装request对象
            Request request = new Request(inputStream);

            Response response = new Response(socket.getOutputStream());

            HttpServlet httpServlet = servletMap.get(request.getUrl());

            if(httpServlet == null){
                response.outputHtml(request.getUrl());
            }else {
                httpServlet.service(request,response);
            }

            socket.close();
        }catch (Exception e){
            e.printStackTrace();

        }


    }
}
