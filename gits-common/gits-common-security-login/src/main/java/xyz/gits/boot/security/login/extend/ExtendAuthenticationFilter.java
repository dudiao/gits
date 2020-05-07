package xyz.gits.boot.security.login.extend;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import xyz.gits.boot.common.security.UserUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author songyinyin
 * @date 2020/5/4 下午 06:20
 */
@Slf4j
public class ExtendAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static String EXTEND_LOGIN_URL = "/open/oauth/callback/**";


    private boolean postOnly = false;

    private AuthRequestFactory authRequestFactory;


    /**
     * 通过构造函数指定该 Filter 要拦截的 url 和 httpMethod
     */
    protected ExtendAuthenticationFilter() {
        // TODO: pattern 可以抽取成配置，最后通过配置文件进行修改，这样作为共用组件只需要实现一个 default，具体值可以有调用者指定
        super(new AntPathRequestMatcher(EXTEND_LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 当设置该 filter 只拦截 post 请求时，符合 pattern 的非 post 请求会触发异常
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            // 1. 从请求中获取参数 用户登录扩展参数
            String extendKey = obtainExtendKey(request);
            String extendType = obtainExtendType(request);
            extendKey = extendKey.trim();


            // 2. 封装成 Token 调用 AuthenticationManager 的 authenticate 方法，该方法中根据 Token 的类型去调用对应 Provider 的 authenticated
            ExtendAuthenticationToken token;
            if (StrUtil.isNotBlank(extendKey)) {
                token = new ExtendAuthenticationToken(extendKey, extendType);
            }else {
                // 从第三方拿到用户信息
                token = new ExtendAuthenticationToken(obtainAuthUser(request));
            }
            this.setDetails(request, token);

            // 3. 返回 authenticated 方法的返回值
            return this.getAuthenticationManager().authenticate(token);
        }
    }

    protected String obtainExtendKey(HttpServletRequest request) {
        return request.getParameter(UserUtil.EXTEND_KEY_PARAMETER);
    }

    protected String obtainExtendType(HttpServletRequest request) {
        return request.getParameter(UserUtil.EXTEND_TYPE_PARAMETER);
    }

    /**
     * 获取 justauth 登录后的用户信息
     */
    protected AuthUser obtainAuthUser(HttpServletRequest request) {
        String type = getCallbackType(request);
        AuthRequest authRequest = authRequestFactory.get(type);

        // 登录后，从第三方拿到用户信息
        AuthResponse response = authRequest.login(getCallback(request));
        log.info("【justauth 第三方登录 response】= {}", JSONUtil.toJsonStr(response));
        // 第三方登录成功
        if (response.getCode() == AuthResponseStatus.SUCCESS.getCode()) {
            AuthUser authUser = (AuthUser) response.getData();
            return authUser;
        }
        return null;
    }

    /**
     * 从请求中构建 AuthCallback
     */
    private AuthCallback getCallback(HttpServletRequest request) {

        AuthCallback authCallback = AuthCallback.builder()
                .code(request.getParameter("code"))
                .auth_code(request.getParameter("auth_code"))
                .authorization_code(request.getParameter("authorization_code"))
                .oauthToken(request.getParameter("oauth_token"))
                .state(request.getParameter("state"))
                .oauthVerifier(request.getParameter("oauth_verifier"))
                .build();

        return authCallback;
    }

    private String getCallbackType(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int common = EXTEND_LOGIN_URL.length() - 2;
        int start = uri.indexOf(EXTEND_LOGIN_URL.substring(0, common));
        return uri.substring(start + common);
    }

    protected void setDetails(HttpServletRequest request, ExtendAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public void setAuthRequestFactory(AuthRequestFactory authRequestFactory) {
        this.authRequestFactory = authRequestFactory;
    }

}
