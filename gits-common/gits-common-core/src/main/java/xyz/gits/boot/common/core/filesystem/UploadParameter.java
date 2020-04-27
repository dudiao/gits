package xyz.gits.boot.common.core.filesystem;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传参数
 *
 * @author dingmingyang
 * @date 2019/12/06/17:12
 */
@Data
@Builder
public class UploadParameter {

    /**
     * 子目录
     */
    private String subDirectory;

    /**
     * 文件名,如:test.java
     */
    private String fileName;

    /**
     * 元数据
     */
    protected Map<String, Object> metadata = new HashMap<String, Object>();

    //------------源文件-------------

    private File file;

    private InputStream inputStream;
}