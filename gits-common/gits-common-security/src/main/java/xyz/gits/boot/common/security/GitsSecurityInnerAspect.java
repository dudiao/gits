package xyz.gits.boot.common.security;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import xyz.gits.boot.common.core.exception.SystemNoLogException;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.utils.ServletUtils;
import xyz.gits.boot.common.security.annotation.Inner;

/**
 * 服务间feign调用不鉴权
 *
 * @author songyinyin
 * @date 2020/8/5 下午 11:51
 */
@Slf4j
@Aspect
public class GitsSecurityInnerAspect {

    @SneakyThrows
    @Around("@annotation(inner)")
    public Object around(ProceedingJoinPoint point, Inner inner) {

        String header = ServletUtils.getRequest().getHeader(SecurityConstant.FROM);
        if (inner.value() && !StrUtil.equals(SecurityConstant.FROM_IN, header)) {
            log.warn("访问接口 [{}] 没有权限", point.getSignature().getName());
            throw new SystemNoLogException(ResponseCode.NO_AUTHENTICATION);
        }
        return point.proceed();
    }
}
