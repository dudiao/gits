package xyz.gits.boot.common.core.filesystem;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件系统
 *
 * @author null
 * @date 2019/12/06/18:04
 */
public interface FileSystemProvider<T extends UploadParameter> extends Closeable {

    /**
     * 文件上传
     *
     * @param parameter 参数
     * @return 文件路径
     */
    String upload(T parameter) throws IOException;

    /**
     * 文件下载
     *
     * @param fileName 文件名，包含文件后缀在内的完整路径，如：abc/test.java
     * @return 文件输入流
     * @throws IOException
     */
    InputStream download(String fileName) throws IOException;

    /**
     * 删除文件
     *
     * @param fileName 文件名，包含文件后缀在内的完整路径，如：abc/test.java
     * @throws IOException
     */
    boolean delete(String fileName);

}
