package xyz.gits.boot.common.security.annotation;

import org.springframework.context.annotation.Import;
import xyz.gits.boot.common.security.GitsClientServerConfiguration;

import java.lang.annotation.*;

/**
 * 使其成为资源服务器
 *
 * @author songyinyin
 * @date 2020/4/27 上午 12:06
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GitsClientServerConfiguration.class})
public @interface EnableGitsClientServer {
}
