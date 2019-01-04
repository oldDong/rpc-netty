package com.dongzj.rpc.client.core;

import com.dongzj.rpc.client.RPC;
import com.dongzj.rpc.client.entity.Address;
import com.dongzj.rpc.client.entity.ServiceParams;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 负载均衡相关
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 15:53
 */
public class LoadBalance {

    public static AtomicInteger count = new AtomicInteger(0);

    /**
     * 获取一个服务器地址
     * 策略：随机
     *
     * @return
     */
    public static Address loadbalanceRandom(String serviceName) {
        ServiceParams serviceParams = RPC.getService(serviceName);
        int total = serviceParams.getAddresses().size();
        int index = (int) (System.currentTimeMillis() % total);

        return serviceParams.getAddresses().get(index);
    }

    public static Address loadbalanceUniformity(String serviceName) {
        ServiceParams serviceParams = RPC.getService(serviceName);
        int total = serviceParams.getAddresses().size();

        return serviceParams.getAddresses().get(count.getAndIncrement() % total);
    }

}
