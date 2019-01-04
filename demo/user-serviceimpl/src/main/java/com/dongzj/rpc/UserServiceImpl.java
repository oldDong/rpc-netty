package com.dongzj.rpc;

import com.dongzj.rpc.contract.UserService;
import com.dongzj.rpc.entity.User;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 19:33
 */
public class UserServiceImpl implements UserService {

    @Override
    public User genericUser(int id, String name, Long phone, Date birthDay) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPhone(phone);
        user.setBirthDay(birthDay);
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> users(List<User> us) {
        System.out.println("服务日志：" + us.size());
        return us;
    }
}
