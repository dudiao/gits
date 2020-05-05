package xyz.gits.boot.common.core.selector;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 选择器工具类
 *
 * @author songyinyin
 * @date 2020/2/17 下午 06:26
 */
public class SelectUtil {

    /**
     * 根据处理器类型，选择匹配的处理器
     *
     * @param processors 该集合泛型需要实现{@link SelectProcessor}
     * @param types      处理器类型 {@link SelectProcessor#type()}
     * @return 选择后的处理器集合
     * @see SelectProcessor
     */
    public static <T> List<T> select(Collection<T> processors, String... types) {
        List<String> typeList = Arrays.asList(types);
        return processors.stream().filter(e -> {
            SelectProcessor selectProcessor = (SelectProcessor) e;
            return typeList.contains(selectProcessor.type());
        }).collect(Collectors.toList());
    }
}
