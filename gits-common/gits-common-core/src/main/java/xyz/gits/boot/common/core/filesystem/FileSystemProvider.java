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
     * @param fileKey 全路径或者fileKey
     * @return 文件输入流
     * @throws IOException
     */
    InputStream download(String fileKey) throws IOException;

    /**
     * 文件下载到本地服务器
     *
     * @param fileKey 全路径或者fileKey
     * @param path    下载到本地服务器全路径
     * @throws IOException
     */
    void downloadLocal(String fileKey, String path) throws IOException;

}
