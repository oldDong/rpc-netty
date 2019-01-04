package com.dongzj.rpc.core;

import com.dongzj.rpc.entity.Global;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 加载配置文件，初始化相关配置
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 11:54
 */
public class LoadConfigure {

    private static final Logger logger = LoggerFactory.getLogger(LoadConfigure.class);

    /**
     * 加载该服务下的配置文件，并初始化相关内容
     *
     * @param serviceRootPath
     */
    public static void load(String serviceRootPath) throws Exception {
        String serviceLib = serviceRootPath + File.separator + "lib";
        String serviceConf = serviceRootPath + File.separator + "conf";

        //读取该服务的配置文件
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(serviceConf + File.separator + "service.xml"));
        document.setXMLEncoding("UTF-8");
        Element node = document.getRootElement();

        Element proNode = node.element("property");

        Element connectionNode = proNode.element("connection");
        Element nettyNode = proNode.element("netty");

        Global.getInstance().setMaxBuf(Integer.parseInt(nettyNode.attributeValue("maxBuf")));
        Global.getInstance().setIp(connectionNode.attributeValue("ip"));

        if (Global.getInstance().getPort() == null) {
            Global.getInstance().setPort(Integer.parseInt(connectionNode.attributeValue("port")));
        } else {
            connectionNode.setAttributeValue("port", String.valueOf(Global.getInstance().getPort()));
            FileOutputStream fos = new FileOutputStream(serviceConf + File.separator + "service.xml");
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            OutputFormat of = new OutputFormat();
            of.setEncoding("UTF-8");
            XMLWriter write = new XMLWriter(osw, of);
            write.write(document);
            write.close();
        }

        Global.getInstance().setTimeout(Integer.parseInt(connectionNode.attributeValue("timeout")));

        Map<String, String> serviceMap = new HashMap<String, String>();
        Element servicesNode = node.element("services");

        List<Element> serviceList = servicesNode.elements("service");
        serviceList.stream().forEach(element -> serviceMap.put(element.attributeValue("name"), element.attributeValue("impl")));
        initService(serviceMap, serviceLib);
    }

    /**
     * 加载该服务所有的jar,并实例化jar中是所有的实现
     *
     * @param services
     * @param serviceLibPath
     */
    private static void initService(Map<String, String> services, String serviceLibPath) throws Exception {
        File serviceLibDir = new File(serviceLibPath);
        File[] jarFiles = serviceLibDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });

        URL[] jarURLS = new URL[jarFiles.length];
        for (int i = 0; i < jarURLS.length; i++) {
            logger.info("加载的类有：" + jarFiles[i].getName());
            jarURLS[i] = jarFiles[i].toURI().toURL();
        }

        URLClassLoader classLoader = new URLClassLoader(jarURLS, ClassLoader.getSystemClassLoader());

        /**
         * 懒加载模式，在启动服务时，初始化所有实现类
         */
        Map<String, Object> instances = new HashMap<>();
        Map<String, Class> types = new HashMap<>();
        Iterator<Map.Entry<String, String>> it = services.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> e = it.next();
            Class clazz = classLoader.loadClass(e.getValue());
            instances.put(e.getKey(), clazz.newInstance());
            types.put(e.getKey(), clazz);
        }

        Global.getInstance().setClassLoader(classLoader);
        Global.getInstance().setServiceImpl(instances);
        Global.getInstance().setServiceClass(types);
    }
}
