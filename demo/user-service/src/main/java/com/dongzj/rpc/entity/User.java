package com.dongzj.rpc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * 需要RPC调用的实体类必须实现Serializable接口，才能进行序列化进行TCP通信
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 19:27
 */
@Data
public class User implements Serializable {

    private Integer id;

    private String name;

    private Long phone;

    private Date birthDay;
}
