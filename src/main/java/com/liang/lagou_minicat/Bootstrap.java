package com.liang.lagou_minicat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bootstrap {

    private Map<String,HttpServlet> servletMap = new HashMap<>();
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

        //1.0
//        while (true){
//
//            Socket socket = serverSocket.accept();
//
//            OutputStream outputStream = socket.getOutputStream();
//
//            String str = "hello world";
//
//            String httpHeader200 = HttpProtocolUtil.getHttpHeader200(str.getBytes().length);
//
//
//            outputStream.write((httpHeader200+str).getBytes());
//
//            socket.close();
//        }

//        //2.0
//        while (true){
//            Socket socket = serverSocket.accept();
//
//            InputStream inputStream = socket.getInputStream();
//
//
//            //封装request对象
//            Request request = new Request(inputStream);
//
//            Response response = new Response(socket.getOutputStream());
//
//            response.outputHtml(request.getUrl());
//
//            socket.close();
//        }

        //3.0
        //解析web.xml
        try {
            loadServlet();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        while (true){

            Socket socket = serverSocket.accept();

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

        }


    }



    //初始化web.xml
    private void loadServlet() throws DocumentException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(resourceAsStream);

        Element rootElement = document.getRootElement();


        List<Element> selectNodes = rootElement.selectNodes("//servlet");

        for (int i = 0; i < selectNodes.size(); i++) {

            Element element = selectNodes.get(i);

            Element servletNameElement = (Element) element.selectSingleNode("servlet-name");

            String servletName = servletNameElement.getStringValue();


            Element servletClassElement = (Element) element.selectSingleNode("servlet-class");

            String servletClass = servletClassElement.getStringValue();


            //根据servlet-name的值找到url-pattern
            Element servletMapping = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");

            String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();


            servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());

        }


        System.out.println(111);

    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
