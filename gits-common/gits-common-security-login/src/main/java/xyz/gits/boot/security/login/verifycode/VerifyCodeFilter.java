package xyz.gits.boot.security.login.verifycode;

import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.gits.boot.common.core.constants.CacheConstants;
import xyz.gits.boot.api.system.utils.AuthUtils;
import xyz.gits.boot.security.login.handler.LoginFailureHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码过滤器
 *
 * @author songyinyin
 * @date 2020/5/31 下午 12:11
 */
@Slf4j
public class VerifyCodeFilter extends OncePerRequestFilter {

    @Autowired
    protected LoginFailureHandler loginFailureHandler;

    @CreateCache(name = CacheConstants.VALIDATE_CODE)
    protected Cache<String, String> codeCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/login", "POST");
        if(matcher.matches(request)){
            try {
                validate(request);
            } catch (VerifyCodeException e) {
                loginFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        // 校验通过，就放行
        filterChain.doFilter(request, response);
    }

    protected void validate(HttpServletRequest request) {
        // 用户输入的验证码
        String code = request.getParameter("code");

        String randomKey = request.getParameter("randomKey");
        if (StrUtil.isBlank(code) || StrUtil.isBlank(randomKey)) {
            log.info("请输入验证码，用户[{}]输入code={}，randomKey={}", AuthUtils.loginUsername(request), code, randomKey);
            throw new VerifyCodeException();
        }
        // 系统生成的验证码
        String cacheCode = codeCache.get(randomKey);
        if (!StrUtil.equals(code, cacheCode)) {
            log.info("登录验证码错误，用户[{}]输入code={}，系统生成code={}", AuthUtils.loginUsername(request), code, cacheCode);
            throw new VerifyCodeException();
        }
        codeCache.remove(randomKey);
    }
}
