package xyz.gits.boot.common.core.constants;

/**
 * 分页信息常量
 *
 * @author songyinyin
 * @date 2020/1/19 14:24
 */
public interface PageParameterConstants {

    /**
     * 当前页默认值
     */
    long PAGE_DEFAULT = 1L;
    /**
     * TODO 需要放到配置中心，每页展示数据大小默认值
     */
    long SIZE_DEFAULT = 10L;
    /**
     * 当前页参数名
     */
    String PAGE = "currentPage";
    /**
     * 每页数据量参数名
     */
    String SIZE = "pageSize";
    /**
     * Request条件查询参数前缀
     */
    String SEARCH_PREFIX = "search_";
    /**
     * 排序条件参数前缀
     */
    String ORDER_PREFIX = "order_";
    /**
     * 查询参数分隔符
     */
    String REGEX = "_";
    /**
     * 倒序排序
     */
    String ORDER_DESC = "DESC";
    /**
     * 正序排序
     */
    String ORDER_ASC = "ASC";
}
