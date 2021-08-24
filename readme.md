项目总结

本项目是一个手写的mini版tomcat

启动流程：

1.先修改minicat/resources/server.xml文件，将appBase指向项目部署的地方
2.将项目放到appBase中，结构是
项目名/WEB-INF
    |-- classes
        |-- com.xxx.xxx.xx
    |-- web.xml
    
3.运行minicat中Bootstrap启动文件，文件启动过程中会读取server.xml，解析server过程中
就会扫描指定的appBase目录下面的所有的项目，将其加载成servlet，

4.通过浏览器请求localhost:8080/项目名/servlet名称 即可获取到输出信息



解析过程：

Bootstrap的start方法是总入口，解析过程分为以下几步

1.调用com.liang.lagou_minicat.ConfigParser的init方法，这个方法加载了server.xml
会解析其中host指定的appBase目录中的文件夹

2.循环每一个文件夹，读取其中的web.xml，将其解析成一个一个的wrapper，包含一个urlPattern和一个servlet。

指定的appBase目录中会有好多项目，每一个项目就是一个context，即servlet容器，
容器中会有好多的servlet，会将这些servlet封装称为wrapper，包含一个urlPattern和一个servlet。

最后形成的结构如下
Mapper->Host->Context->Wrapper->servlet



请求过来后，会通过url去查找一个可用的servlet，如果找到了，则运行其中的service方法，如果找不到则认为是读取静态资源，如果静态资源也找不到则返回404




