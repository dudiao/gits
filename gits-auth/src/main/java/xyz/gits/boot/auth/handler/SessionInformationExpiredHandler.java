package xyz.gits.boot.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import xyz.gits.boot.common.response.ResponseCode;
import xyz.gits.boot.common.response.RestResponse;
import xyz.gits.boot.common.utils.ServletUtils;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 超出最大登录数，用户被踢出后处理方法
 *
 * @author songyinyin
 * @date 2020/2/25 下午 09:55
 */
@Slf4j
@Component
public class SessionInformationExpiredHandler implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {

        ServletUtils.render(sessionInformationExpiredEvent.getRequest(),
                sessionInformationExpiredEvent.getResponse(), RestResponse.fail(ResponseCode.USER_MAX_LOGIN));
    }
}
