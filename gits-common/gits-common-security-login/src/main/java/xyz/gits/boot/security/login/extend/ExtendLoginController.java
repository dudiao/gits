package xyz.gits.boot.security.login.extend;

import com.xkcoding.justauth.AuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/open/oauth")
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/open/oauth/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

}
