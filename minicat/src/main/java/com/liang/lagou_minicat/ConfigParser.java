package com.liang.lagou_minicat;

import com.liang.minicat.servlet.api.HttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigParser {

    public Map<String, HttpServlet> servletMap = new HashMap<>();

    private Mapper mapper = new Mapper();


    public void init() throws DocumentException, ClassNotFoundException, FileNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException {

        InputStream resourceAsStream = ConfigParser.class.getClassLoader().getResourceAsStream("server.xml");


        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(resourceAsStream);

        Element rootElement = document.getRootElement();

        List<Element> hosts = rootElement.selectNodes("//Host");

        for (int i = 0; i < hosts.size(); i++) {

            Host host = new Host();

            Element hostElement = hosts.get(i);

            String hostname = hostElement.attributeValue("name");
            String appBase = hostElement.attributeValue("appBase");


            File folder = new File(appBase);

            if(folder.exists() && folder.isDirectory()){
                File[] webappsFolders = folder.listFiles();

                for (File file : webappsFolders) {

                    if(file.isDirectory()){
                        Context context = new Context();

                        context.setPath(file.getName());

                        File webXmlFile = new File(file,"WEB-INF/web.xml");

                        List<Wrapper> wrappers = loadWebXml(webXmlFile.getAbsolutePath());

                        context.setWrapperList(wrappers);


                        host.getContexts().add(context);
                    }

                }

                host.setName(hostname);

                mapper.getHosts().add(host);
            }


        }


    }




    private List<Wrapper> loadWebXml(String path) throws FileNotFoundException, ClassNotFoundException, IllegalAccessException, InstantiationException, DocumentException, MalformedURLException {

        List<Wrapper> list = new ArrayList<>();

        File file = new File(path);

        FileInputStream fileInputStream = new FileInputStream(file);

        SAXReader saxReader = new SAXReader();

        Document document = saxReader.read(fileInputStream);

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

            urlPattern = "/"+file.getParentFile().getParentFile().getName()+urlPattern;


            //加载servlet.class文件

            String absPath = file.getParentFile().getParentFile().getAbsolutePath();

            WebappClassLoader webappClassLoader = new WebappClassLoader(absPath);
            Class<?> aClass = webappClassLoader.findClass(servletClass);

            servletMap.put(urlPattern, (HttpServlet) aClass.newInstance());

            System.out.println(urlPattern+"加入servlet容器");
        }


        return list;

    }

    public Map<String, HttpServlet> getServletMap() {
        return servletMap;
    }

    public void setServletMap(Map<String, HttpServlet> servletMap) {
        this.servletMap = servletMap;
    }
}
