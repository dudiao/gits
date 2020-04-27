package xyz.gits.boot.common.core.basic;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.gits.boot.common.core.constants.PageParameterConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 业务层基类实现
 *
 * @author songyinyin
 * @date 2020/1/19 14:20
 */
@Slf4j
public class BasicServiceImpl<M extends BasicMapper<T>, T> extends ServiceImpl<BasicMapper<T>, T> implements BasicService<T> {

    /**
     * 解析Request请求中的查询参数，返回QueryWrapper对象，如参数格式非指定格式，自行加工。<br/>
     * 参数格式：search_条件关键字_字段名=参数值<br/>
     * 例如：
     * <p>
     * <tt>search_like_user_name=admin</tt>，会解析为：<tt>QueryWrapper.like("user_name", admin)</tt>
     * <tt>search_in_user_id=1,2</tt>，会解析为：<tt>QueryWrapper.in("user_id", 1, 2)</tt>
     * <tt>order_user_id=desc</tt>，会解析为：<tt>QueryWrapper.orderByDesc("user_id")</tt>
     * </p>
     *
     * @return
     */
    @Override
    public QueryWrapper<T> parseParameter() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 构造查询条件
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 获取查询条件列表
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            // 参数Key
            String paraName = parameterNames.nextElement();
            // 查询条件
            if (paraName.toLowerCase().startsWith(PageParameterConstants.SEARCH_PREFIX)) {
                // search_like_user_name=admin
                String[] split = paraName.split(PageParameterConstants.REGEX);
                // admin
                String searchValue = request.getParameter(paraName);
                // 跳过不合法的查询参数
                if (split.length < 3) {
                    log.warn("[解析参数] - 查询参数非法：{}={}", paraName, searchValue);
                    continue;
                }
                int regexIndex = paraName.indexOf(PageParameterConstants.REGEX);
                // like_user_name
                String str = paraName.substring(regexIndex + 1);
                // like
                String searchType = str.substring(0, str.indexOf(PageParameterConstants.REGEX));
                // user_name
                String searchFiled = str.substring(str.indexOf(PageParameterConstants.REGEX) + 1);

                switch (searchType.toLowerCase()) {
                    case "eq":
                        if (StrUtil.isNotBlank(searchValue)) {
                            queryWrapper.eq(searchFiled, searchValue);
                        }
                        break;
                    case "like":
                        if (StrUtil.isNotBlank(searchValue)) {
                            queryWrapper.like(searchFiled, searchValue);
                        }
                        break;
                    case "ne":
                        if (StrUtil.isNotBlank(searchValue)) {
                            queryWrapper.ne(searchFiled, searchValue);
                        }
                        break;
                    case "gt":
                        if (StrUtil.isNotBlank(searchValue)) {
                            queryWrapper.gt(searchFiled, searchValue);
                        }
                        break;
                    case "lt":
                        if (StrUtil.isNotBlank(searchValue)) {
                            queryWrapper.lt(searchFiled, searchValue);
                        }
                        break;
                    case "in":
                        // search_in_user_id=1,2
                        if (StrUtil.isBlank(searchValue)) {
                            break;
                        }
                        String[] searchValues = searchValue.split(",");
                        if (searchValues.length > 0 && searchValues.length < 1000) {
                            queryWrapper.in(searchFiled, searchValues);
                        }
                        break;
                    default:
                        log.warn("[解析参数] - 查询条件无法解析：{}={}", paraName, searchValue);
                }
            } else if (paraName.toLowerCase().startsWith(PageParameterConstants.ORDER_PREFIX)) {
                // 排序规则 order_user_id=desc
                // user_id
                String orderColumn = paraName.substring(paraName.indexOf(PageParameterConstants.REGEX) + 1);
                // desc
                String orderType = request.getParameter(paraName);
                if (StrUtil.isNotBlank(orderColumn)) {
                    if (PageParameterConstants.ORDER_DESC.equals(orderType.toUpperCase())) {
                        queryWrapper.orderByDesc(orderColumn);
                    } else if (PageParameterConstants.ORDER_ASC.equals(orderType.toUpperCase())) {
                        queryWrapper.orderByAsc(orderColumn);
                    }
                    log.warn("[解析参数] - 排序参数非法：{}={}", paraName, orderType);
                }
                log.warn("[解析参数] - 排序参数非法：{}={}", paraName, orderType);
            }
        }
        log.debug("[解析参数] - 全部符合规范的请求参数:{}", new JSONObject(queryWrapper).toString());
        return queryWrapper;
    }

    /**
     * 解析分页参数
     *
     * @return
     */
    @Override
    public Page<T> parsePage() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        // 解析分页信息，默认查询第1页，每页10条
        Page<T> page = new Page<>();
        String currentPage = request.getParameter(PageParameterConstants.PAGE);
        String pageSize = request.getParameter(PageParameterConstants.SIZE);
        if (StrUtil.isNotBlank(currentPage)) {
            page.setCurrent(Long.parseLong(currentPage));
        } else {
            page.setCurrent(PageParameterConstants.PAGE_DEFAULT);
            log.debug("[解析参数] - 查询页数无效,使用缺省值{}", PageParameterConstants.PAGE_DEFAULT);
        }
        if (StrUtil.isNotBlank(pageSize)) {
            page.setSize(Long.parseLong(pageSize));
        } else {
            page.setSize(PageParameterConstants.SIZE_DEFAULT);
            log.debug("[解析参数] - 查询数据大小无效,使用缺省值{}", PageParameterConstants.SIZE_DEFAULT);
        }
        return page;
    }

    /**
     * 分页查询
     *
     * @param wrapper 查询条件
     * @return 分页结果
     */
    @Override
    public IPage<T> page(Wrapper<T> wrapper) {
        return this.baseMapper.selectPage(parsePage(), wrapper);
    }
}
