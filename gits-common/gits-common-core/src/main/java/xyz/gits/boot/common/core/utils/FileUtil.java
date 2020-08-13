package xyz.gits.boot.common.core.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.gits.boot.common.core.exception.SystemException;
import xyz.gits.boot.common.core.filesystem.FileSystemProvider;
import xyz.gits.boot.common.core.filesystem.UploadParameter;
import xyz.gits.boot.common.core.response.ResponseCode;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;


/**
 * 文件上传工具
 *
 * @author null
 * @date 2019/12/11/16:47
 */
@Slf4j
public class FileUtil {

    private static FileSystemProvider fileSystemProvider = SpringContextHolder.getBean(FileSystemProvider.class);

    /**
     * 文件名前随机数的位数（为了防止同一目录下，文件名重复）
     */
    private static final int RANDOM_BITS = 8;

    /**
     * 文件上传
     *
     * @param subdirectory 子目录：system
     * @return 文件存放全路径
     */
    @SneakyThrows
    public static String upload(String subdirectory, MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            originalFilename = "tmpFileName";
        }
        // 兼容ie9:去除文件路径最后'\\'之前所有字符
        if (StrUtil.contains(originalFilename, CharUtil.BACKSLASH)) {
            originalFilename = StrUtil.subAfter(originalFilename, CharUtil.BACKSLASH, true);
        }

        return upload(subdirectory + CharUtil.SLASH + originalFilename, multipartFile.getInputStream());
    }

    /**
     * 文件上传
     *
     * @param fileName    文件上传名称，包含文件后缀在内的完整路径，如：system/test.java
     * @param inputStream 输入流
     * @return 文件存放全路径 如：/opt/file/system/81723931-test.java （上传源文件：test.java）
     */
    public static String upload(String fileName, InputStream inputStream) {
        try {
            String normalizeFileName = cn.hutool.core.io.FileUtil.normalize(fileName);

            // 将文件名加上前缀 如：system/test.java --->  system/81723931-test.java , 下载时文件名切掉前缀即可。
            int index = normalizeFileName.lastIndexOf(CharUtil.SLASH) + 1;
            String randomFileName = normalizeFileName.substring(0, index)
                + IdUtil.simpleUUID().substring(0, RANDOM_BITS) + CharUtil.DASHED
                + normalizeFileName.substring(index);

            UploadParameter parameter = UploadParameter.builder()
                .uploadFileName(randomFileName)
                .inputStream(inputStream)
                .build();
            return fileSystemProvider.upload(parameter);
        } catch (IOException e) {
            throw new SystemException(ResponseCode.FILE_UPLOAD_EXCEPTION, e);
        }
    }

    /**
     * 文件下载
     *
     * @param fileName  文件全路径 如:/opt/file/system/81723931-test.java
     * @param response
     */
    public static void download(String fileName, HttpServletResponse response) {
        InputStream inputStream = null;
        ServletOutputStream outputStream = null;
        try {
            // 将文件名去除前缀 如： 81723931-test.java ---> test.java
            String realFileName = cn.hutool.core.io.FileUtil.getName(fileName).substring(RANDOM_BITS + 1);
            response.reset();
            response.setContentType("application/octet-stream");
            // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            inputStream = fileSystemProvider.download(realFileName);
            outputStream = response.getOutputStream();
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                throw new SystemException(ResponseCode.FILE_NOT_EXIST, e);
            }
            throw new SystemException(ResponseCode.FILE_DOWNLOAD_ABNORMAL, e);
        } finally {
            IoUtil.close(inputStream);
            IoUtil.close(outputStream);
        }
    }

}