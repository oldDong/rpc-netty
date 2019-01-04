package com.dongzj.rpc.common.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 压缩相关工具
 * <p>
 * User: dongzj
 * Mail: dongzj@shinemo.com
 * Date: 2018/11/13
 * Time: 11:16
 */
public class CompressUtil {

    /**
     * 压缩工具
     *
     * @param input
     * @return
     */
    public static byte[] compress(byte[] input) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Deflater compressor = new Deflater(1);
        try {
            compressor.setInput(input);
            compressor.finish();
            final byte[] buf = new byte[2048];
            while (!compressor.finished()) {
                int count = compressor.deflate(buf);
                bos.write(buf, 0, count);
            }
        } finally {
            compressor.end();
        }
        return bos.toByteArray();
    }

    /**
     * 解压缩
     *
     * @param input
     * @return
     */
    public static byte[] uncompress(byte[] input) throws DataFormatException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Inflater decompressor = new Inflater();
        try {
            decompressor.setInput(input);
            final byte[] buf = new byte[2048];
            while (!decompressor.finished()) {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            }
        } finally {
            decompressor.end();
        }
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        String inputStr = "stan lee, hello world againstan lee, hello world againstan lee, hello world againstan lee, hello world againstan lee, hello world again";
        byte[] bytes = inputStr.getBytes();
        System.out.println("bytes: " + bytes.length);
        byte[] compressed = compress(bytes);
        System.out.println("compressed: " + compressed.length);
    }
}
