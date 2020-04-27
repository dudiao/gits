package xyz.gits.boot.common.core.basic;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 业务层基类
 *
 * @author songyinyin
 * @date 2020/1/19 14:14
 */
public interface BasicService<T> extends IService<T> {

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
    QueryWrapper<T> parseParameter();

    /**
     * 解析分页参数
     *
     * @return
     */
    Page<T> parsePage();

    /**
     * 分页查询
     *
     * @param wrapper 查询条件
     * @return 分页结果
     */
    IPage<T> page(Wrapper<T> wrapper);
}
