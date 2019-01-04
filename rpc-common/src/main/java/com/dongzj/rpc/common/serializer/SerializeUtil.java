package com.dongzj.rpc.common.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 10:52
 */
public class SerializeUtil {

    private static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 对象序列化
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.error("serialize error", e);
        }
        return null;
    }

    /**
     * 反序列化，需要指明反序列化的类的ClassLoader
     *
     * @param bytes
     * @param classLoader
     * @return
     */
    public static Object deserialize(byte[] bytes, ClassLoader classLoader) {
        ByteArrayInputStream bais = null;
        ObjectInputStreamWithLoader ois = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStreamWithLoader(bais, classLoader);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("deserialize error", e);
        }
        return null;
    }

    /**
     * 对象反序列化（反序列化类为AppClassLoader加载）
     *
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            logger.error("deserialize error", e);
        }
        return null;
    }
}
