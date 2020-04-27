package xyz.gits.boot.common.security.hander;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import xyz.gits.boot.common.core.response.ResponseCode;
import xyz.gits.boot.common.core.response.RestResponse;
import xyz.gits.boot.common.core.utils.ServletUtils;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 超出最大登录数，用户被踢出后处理方法
 *
 * @author songyinyin
 * @date 2020/2/25 下午 09:55
 */
@Slf4j
public class SessionInformationExpiredHandler implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {

        ServletUtils.render(sessionInformationExpiredEvent.getRequest(),
                sessionInformationExpiredEvent.getResponse(), RestResponse.fail(ResponseCode.USER_MAX_LOGIN));
    }
}
