package com.dongzj.rpc.common.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hession序列化及反序列化工具
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 10:39
 */
public class HessianUtil {

    /**
     * 序列化对象
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        if (obj == null) {
            throw new NullPointerException();
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param by
     * @param classLoader
     * @return
     */
    public static Object deserialize(byte[] by, ClassLoader classLoader) throws IOException {
        if (by == null) {
            throw new NullPointerException();
        }
        ByteArrayInputStream is = new ByteArrayInputStream(by);
        ClassLoader old = null;
        if (classLoader != null) {
            old = Thread.currentThread().getContextClassLoader();
            //且含当前线程classloader，保证动态加载的类不会报ClassNotFoundException
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        HessianInput hi = new HessianInput(is);
        Object obj = hi.readObject();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(old);
        }
        return obj;
    }

}
