package com.dongzj.rpc.common.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * 反序列化中使用自定义加载的类
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 10:56
 */
public class ObjectInputStreamWithLoader extends ObjectInputStream {

    private ClassLoader loader;


    public ObjectInputStreamWithLoader(InputStream in, ClassLoader loader) throws IOException {
        super(in);
        if (loader == null) {
            throw new IllegalArgumentException("Illegal null argument to ObjectInputStreamWithLoader");
        }
        this.loader = loader;
    }

    @Override
    protected Class resolveClass(ObjectStreamClass classDesc) throws ClassNotFoundException {
        String className = classDesc.getName();
        return loader.loadClass(className);
    }
}
