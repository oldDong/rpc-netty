package com.dongzj.rpc.client.net;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 17:03
 */
public class ReceiverData {

    /**
     * 服务端返回的数据
     */
    private byte[] data;

    /**
     * 用于阻塞查询数据
     */
    private CountDownLatch countDownLatch;

    public ReceiverData() {
        countDownLatch = new CountDownLatch(1);
    }

    public byte[] getData(long waitTime) throws InterruptedException {
        countDownLatch.await(waitTime, TimeUnit.MILLISECONDS);
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        countDownLatch.countDown();
    }
}
