package com.dongzj.rpc.client;

import com.dongzj.rpc.client.entity.Address;
import com.dongzj.rpc.client.entity.ServiceParams;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 14:59
 */
public class RPC {

    private static Map<String, ServiceParams> serviceCache = new HashMap<>();

    /**
     * 初始化客户端配置文件
     *
     * @param clientPath
     */
    public static void init(String clientPath) throws Exception {
        //读取该服务的配置文件
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(clientPath));
        Element root = document.getRootElement();

        List<Element> serviceNodes = root.elements("Service");
        serviceNodes.stream().forEach(serviceNode -> {
            ServiceParams serviceParams = new ServiceParams();
            serviceParams.setServiceName(serviceNode.attributeValue("name"));

            Element loadBalanceNode = serviceNode.element("Loadbalance");
            Element serverNode = loadBalanceNode.element("Server");
            serviceParams.setTimeout(Integer.parseInt(serverNode.attributeValue("timeout")));
            List<Element> addrNodes = serverNode.elements("addr");

            addrNodes.stream().forEach(addrNode -> {
                Address addr = new Address();
                addr.setName(addrNode.attributeValue("name"));
                addr.setHost(addrNode.attributeValue("host"));
                addr.setPort(Integer.parseInt(addrNode.attributeValue("port")));

                serviceParams.addAddress(addr);
            });

            serviceCache.put(serviceParams.getServiceName(), serviceParams);
        });
    }

    /**
     * 获取服务配置
     *
     * @param serviceName
     * @return
     */
    public static ServiceParams getService(String serviceName) {
        return serviceCache.get(serviceName);
    }
}
