package xyz.gits.boot.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 同时支持 sessionId 存到 cookie，header 和 request parameter
 *
 * @author songyinyin
 * @date 2020/3/18 下午 05:53
 */
public class RestHttpSessionIdResolver implements HttpSessionIdResolver {

    public static final String AUTH_TOKEN = "GitsSessionID";

    private String sessionIdName = AUTH_TOKEN;

    private CookieHttpSessionIdResolver cookieHttpSessionIdResolver;

    public RestHttpSessionIdResolver() {
        initCookieHttpSessionIdResolver();
    }

    public RestHttpSessionIdResolver(String sessionIdName) {
        this.sessionIdName = sessionIdName;
        initCookieHttpSessionIdResolver();
    }

    public void initCookieHttpSessionIdResolver() {
        this.cookieHttpSessionIdResolver = new CookieHttpSessionIdResolver();
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName(this.sessionIdName);
        this.cookieHttpSessionIdResolver.setCookieSerializer(cookieSerializer);
    }


    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        // cookie
        List<String> cookies = cookieHttpSessionIdResolver.resolveSessionIds(request);
        if (CollUtil.isNotEmpty(cookies)) {
            return cookies;
        }
        // header
        String headerValue = request.getHeader(this.sessionIdName);
        if (StrUtil.isNotBlank(headerValue)) {
            return Collections.singletonList(headerValue);
        }
        // request parameter
        String sessionId = request.getParameter(this.sessionIdName);
        return (sessionId != null) ? Collections.singletonList(sessionId) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        response.setHeader(this.sessionIdName, sessionId);
        this.cookieHttpSessionIdResolver.setSessionId(request, response, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(this.sessionIdName, "");
        this.cookieHttpSessionIdResolver.setSessionId(request, response, "");
    }
}
