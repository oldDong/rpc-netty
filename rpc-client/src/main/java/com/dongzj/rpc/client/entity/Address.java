package com.dongzj.rpc.client.entity;

import lombok.Data;

/**
 * 连接参数
 *
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 14:54
 */
@Data
public class Address {

    private String name;

    private String host;

    private Integer port;
}
