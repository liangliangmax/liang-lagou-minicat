package com.liang.lagou_minicat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StaticResourcesUtil {

    public static String getAbsolutePath(String path){

        String absolutePath = StaticResourcesUtil.class.getResource("/").getPath();

        return absolutePath.replaceAll("\\\\","/")+path;


    }


    public static void outputStaticResources(InputStream inputStream, OutputStream outputStream) throws IOException {


        int count = 0;

        while(count ==0){
            count = inputStream.available();

        }

        int resourcesSize = count;

        outputStream.write(HttpProtocolUtil.getHttpHeader200(resourcesSize).getBytes());


        long written = 0;

        int byteSize = 1024;

        byte[] bytes = new byte[byteSize];

        while (written < resourcesSize){
            if(written + byteSize > resourcesSize){

                byteSize = (int) (resourcesSize - written); //剩余的长度

                bytes = new byte[byteSize];
            }

            inputStream.read(bytes);
            outputStream.write(bytes);

            outputStream.flush();

            written+=byteSize;

        }


    }
}
