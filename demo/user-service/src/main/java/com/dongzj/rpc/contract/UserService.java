package com.dongzj.rpc.contract;

import com.dongzj.rpc.entity.User;

import java.util.Date;
import java.util.List;

/**
 * 用户服务接口
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 19:30
 */
public interface UserService {

    User genericUser(int id, String name, Long phone, Date birthDay);

    List<User> users(List<User> us);
}
