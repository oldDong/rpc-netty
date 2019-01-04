package com.dongzj.rpc;

import com.dongzj.rpc.client.RPC;
import com.dongzj.rpc.client.proxy.ProxyFactory;
import com.dongzj.rpc.contract.UserService;
import com.dongzj.rpc.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 服务调用者
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 19:45
 */
public class App {

    public static void main(String[] args) {
        Long start = 0L;
        try {
            final Logger log = LoggerFactory.getLogger(App.class);

            RPC.init("src/main/resources/client.xml");

            //通过代理获取接口类，第二个参数为client.xml文件中服务的名字，第三个参数为接口具体实现的名字，需要跟该服务的配置文件的name的值一样
            UserService service = ProxyFactory.create(UserService.class, "user", "userService");

            Executor pool = Executors.newFixedThreadPool(200);
            final CountDownLatch count = new CountDownLatch(100000);
            start = System.currentTimeMillis();

            for (int i = 0; i < 100000; i++) {
                pool.execute(new Task(service, i, log, count));
            }
            count.await();
            System.out.println(System.currentTimeMillis() - start);
            System.out.println("执行完毕");
        } catch (Exception e) {
            System.out.println(System.currentTimeMillis() - start);
            e.printStackTrace();
        }
    }

    public static class Task implements Runnable {

        UserService userService;

        int id;

        Logger log;

        CountDownLatch countDownLatch;

        public Task(UserService userService, int id, Logger log, CountDownLatch countDownLatch) {
            this.userService = userService;
            this.id = id;
            this.log = log;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            List<User> users = new ArrayList<>();

            for (int i = 0; i < 20; i++) {
                User user = new User();
                user.setId(id);
                user.setName("name: " + id);
                user.setBirthDay(new Date());
                user.setPhone(188L);

                users.add(user);
            }
            long start = System.currentTimeMillis();
            List<User> s = userService.users(users);
            try {
                if (s.get(0).getId() != id) {
                    System.out.println("不正确的数据, sessionID:" + id);
                } else {
                    System.out.println("数据正确：" + id);
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("调用错误", e);
            }
        }
    }
}
