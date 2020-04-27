package xyz.gits.boot.common.security.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import xyz.gits.boot.common.security.GitsClientServerConfiguration;
import xyz.gits.boot.common.security.PermissionService;

import java.lang.annotation.*;

/**
 * 使其成为资源服务器
 *
 * <p>
 * 'prePostEnabled = true' --> 使{@link PreAuthorize PostAuthorize} {@link PostAuthorize}和{@link PermissionService}生效 <br/>
 * <p>
 * 'securedEnabled = true' --> 使{@link Secured}生效
 * </p>
 *
 * @author songyinyin
 * @date 2020/4/27 上午 12:06
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import({GitsClientServerConfiguration.class})
public @interface EnableGitsClientServer {
}
