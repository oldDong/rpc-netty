package com.dongzj.rpc.client.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务参数
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 14:53
 */
@Data
public class ServiceParams {

    private int timeout;

    private List<Address> addresses;

    private String serviceName;

    public void addAddress(Address address) {
        if (address == null) {
            addresses = new ArrayList<>();
        }
        addresses.add(address);
    }

    public void removeAddress(int index) {
        if (addresses != null) {
            addresses.remove(index);
        }
    }
}
