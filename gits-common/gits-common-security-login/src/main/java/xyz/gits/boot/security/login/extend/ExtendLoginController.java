package xyz.gits.boot.security.login.extend;

import cn.hutool.core.io.IoUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.wf.captcha.ArithmeticCaptcha;
import com.xkcoding.justauth.AuthRequestFactory;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.gits.boot.common.core.config.GitsProperties;
import xyz.gits.boot.common.core.constants.CacheConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private static final Integer DEFAULT_IMAGE_WIDTH = 100;
    private static final Integer DEFAULT_IMAGE_HEIGHT = 40;

    @CreateCache(name = CacheConstants.VALIDATE_CODE)
    private Cache<String, String> codeCache;

    @Autowired
    private GitsProperties properties;

    @GetMapping("/open/oauth")
    public List<String> list() {
        return factory.oauthList();
    }

    @GetMapping("/open/oauth/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @GetMapping("/open/code")
    public ResponseEntity validateCode(@ApiParam(name = "randomKey", value = "验证码随机数key") @RequestParam("randomKey") String randomKey) {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
        String result = captcha.text();

        // 缓存验证码
        codeCache.put(randomKey, result, properties.getSecurity().getCodeExpireTime(), TimeUnit.SECONDS);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.out(os);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity(os.toByteArray(), headers, HttpStatus.CREATED);
    }

}
