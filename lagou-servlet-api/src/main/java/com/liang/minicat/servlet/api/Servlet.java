package com.liang.minicat.servlet.api;

import java.io.IOException;

public interface Servlet {

    void init();

    void destory();

    void service(Request request, Response response) throws IOException;
}
