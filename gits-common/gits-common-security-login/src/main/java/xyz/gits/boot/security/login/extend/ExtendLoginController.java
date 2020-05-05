package xyz.gits.boot.security.login.extend;

import cn.hutool.json.JSONUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 集成 justauth
 *
 * @author songyinyin
 * @date 2020/5/5 下午 06:36
 */
@Slf4j
@RestController
public class ExtendLoginController {

    @Autowired
    private AuthRequestFactory factory;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/open/oauth")
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/open/oauth/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/open/oauth/callback/{type}")
    public AuthResponse login(@PathVariable String type, AuthCallback callback) {
        AuthRequest authRequest = factory.get(type);
        // 登录后，从第三方拿到用户信息
        AuthResponse response = authRequest.login(callback);
        log.info("【response】= {}", JSONUtil.toJsonStr(response));
        // 第三方登录成功
        if (response.getCode() == AuthResponseStatus.SUCCESS.getCode()) {
            AuthUser authUser = (AuthUser) response.getData();
            // 走系统内登录流程，获取token，最后重定向
            Authentication authentication = authenticationManager.authenticate(new ExtendAuthenticationToken(authUser));
            log.info("【内部登录】= {}", JSONUtil.toJsonStr(authentication));
        }
        return response;
    }
}
