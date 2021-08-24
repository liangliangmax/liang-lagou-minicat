package com.liang.minicat.servlet.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {

    private OutputStream outputStream;


    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }


    public void outputHtml(String path) throws IOException {

        String absoluteResourcsPath = StaticResourcesUtil.getAbsolutePath(path);


        File file = new File(absoluteResourcsPath);

        if(file.exists() && file.isFile()){
            StaticResourcesUtil.outputStaticResources(new FileInputStream(file),outputStream);

        }else {
            //404
            output(HttpProtocolUtil.getHttpHeader404());

        }


    }

    public void output(String content) throws IOException {

        outputStream.write(content.getBytes());

    }

}
