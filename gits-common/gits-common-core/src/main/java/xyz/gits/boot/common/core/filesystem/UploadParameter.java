package xyz.gits.boot.common.core.filesystem;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传参数
 *
 * @author null
 * @date 2019/12/06/17:12
 */
@Data
@Builder
public class UploadParameter {

    /**
     * 文件上传名称，包含文件后缀在内的完整路径，如：abc/test.java
     */
    private String uploadFileName;

    /**
     * 元数据
     */
    protected Map<String, Object> metadata = new HashMap<String, Object>();

    //------------源文件-------------

    private InputStream inputStream;
}