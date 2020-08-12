package xyz.gits.boot.common.core.filesystem;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import xyz.gits.boot.common.core.exception.SystemException;
import xyz.gits.boot.common.core.response.ResponseCode;

import java.io.*;

/**
 * 默认的文件系统实现，存储在本地硬盘上。
 *
 * @author null
 * @date 2019/12/10/15:05
 */
@Slf4j
@Setter
public class SimpleFileSystemProvider<T extends UploadParameter> extends AbstractFileSystemProvider<T> {

    /**
     * 服务器根路径 如:/opt/file
     */
    private String rootPath;


    /**
     * 文件上传服务器
     *
     * @param parameter 文件上传参数对象
     * @return fileName 文件名，包含文件后缀在内的完整路径，如：/opt/file/abc/test.java
     */
    @Override
    public String upload(T parameter) {

        InputStream inputStream = null;
        try {
            if (StrUtil.isBlank(parameter.getUploadFileName())) {
                throw new IORuntimeException("文件名为空");
            }
            inputStream = parameter.getInputStream();
            // 拼接全路径
            String url = rootPath + "/" + parameter.getUploadFileName();
            // 写入文件
            File file = FileUtil.writeFromStream(inputStream, url);
            return file.getPath();
        } finally {
            // 关闭流
            IoUtil.close(inputStream);
        }

    }

    /**
     * 从服务器上下载获取到输入流，不关闭流
     *
     * @param fileName 文件名，包含文件后缀在内的完整路径，如：/opt/file/abc/test.java
     * @return 文件输入流 使用该方法需要手动关闭流 如果要下载的文件太大，或者一次性下载耗时太长，您可以通过流式下载，一次处理部分内容，直到完成文件的下载。
     */
    @Override
    public InputStream download(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        if (!file.exists()) {
            throw new SystemException(ResponseCode.FILE_NOT_EXIST, fileName);
        }
        return fileInputStream;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名，包含文件后缀在内的完整路径，如：/opt/file/abc/test.java
     */
    @Override
    public boolean delete(String fileName) {
        if (StrUtil.isBlank(fileName) || !fileName.startsWith(rootPath)) {
            throw new SystemException(ResponseCode.FILE_DELETE_FAILE, "fileName=" + fileName);
        }
        return FileUtil.del(fileName);
    }

    @Override
    public void close() throws IOException {

    }
}