package com.liang.demo.demo1;



import com.liang.minicat.servlet.api.HttpProtocolUtil;
import com.liang.minicat.servlet.api.HttpServlet;
import com.liang.minicat.servlet.api.Request;
import com.liang.minicat.servlet.api.Response;

import java.io.IOException;

public class LiangServlet extends HttpServlet {


    @Override
    public void doGet(Request request, Response response) throws IOException {

        String content = "<h1>liang servlet get</h1>";

        response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length)+content);

    }

    @Override
    public void doPost(Request request, Response response) throws IOException {

        String content = "<h1>liang servlet post</h1>";

        response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length)+content);


    }

    @Override
    public void init() {

    }

    @Override
    public void destory() {

    }
}
