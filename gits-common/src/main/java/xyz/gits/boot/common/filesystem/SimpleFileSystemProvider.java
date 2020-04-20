package xyz.gits.boot.common.filesystem;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 默认的文件系统实现，存储在本地硬盘上。
 *
 * @author dingmingyang
 * @date 2019/12/10/15:05
 */
@Slf4j
@Setter
public class SimpleFileSystemProvider extends AbstractFileSystemProvider {

    /**
     * 服务器根路径 如:/opt/file
     */
    private String rootPath;


    /**
     * 文件上传服务器
     *
     * @param parameter 文件上传参数对象
     * @return 文件存放全路径
     */
    @Override
    public String upload(UploadParameter parameter) throws IOException {

        InputStream inputStream = null;
        try {
            //子目录
            String subDirectory = parameter.getSubDirectory();
            if (StrUtil.isBlank(parameter.getFileName())) {
                throw new IORuntimeException("文件名为空");
            }
            inputStream = parameter.getInputStream();
            // 拼接子目录
            String url = StrUtil.isBlank(subDirectory) ? rootPath + "/" + parameter.getFileName() : rootPath + subDirectory + "/" + parameter.getFileName();
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
     * @param fileKey 文件全路径 如:/opt/file/test.java
     * @return 文件输入流 使用该方法需要手动关闭流 如果要下载的文件太大，或者一次性下载耗时太长，您可以通过流式下载，一次处理部分内容，直到完成文件的下载。
     */
    @Override
    public InputStream download(String fileKey) throws IOException {
        File file = new File(fileKey);
        FileInputStream fileInputStream = new FileInputStream(file);
        if (!file.exists()) {
            throw new FileNotFoundException(fileKey);
        }
        return fileInputStream;
    }

    /**
     * 文件下载到本地服务器（相当于复制）
     *
     * @param fileKey 文件全路径 如:/opt/file/test.java
     * @param path    文件下载存放的路径 如:/usr/local/file/test.java
     */
    @Override
    public void downloadLocal(String fileKey, String path) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            File file = new File(fileKey);
            fileInputStream = new FileInputStream(file);
            if (!file.exists()) {
                throw new FileNotFoundException(fileKey);
            }
            FileUtil.writeFromStream(fileInputStream, path);
        } finally {
            IoUtil.close(fileInputStream);
        }

    }

    @Override
    public void close() throws IOException {

    }
}