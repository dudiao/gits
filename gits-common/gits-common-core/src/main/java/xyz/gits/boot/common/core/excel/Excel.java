package xyz.gits.boot.common.core.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义导入导出Excel数据注解
 *
 * @author songyinyin
 * @date 2019/12/6 14:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
    /**
     * 导出到Excel中的名字
     */
    String name();

    /**
     * 日期格式, 如: yyyy-MM-dd HH:mm:ss
     */
    String dateFormat() default "";

    /**
     * 读取内容转表达式 (如: "0=男,1=女,2=未知")
     */
    String readConverterExp() default "";

    /**
     * 字典转换（同一字段需要转换多个字典时，逗号隔开例如 "CATP,CITP" ，按照排列顺序，只返回一个结果）
     */
    String converteDict() default "";

    /**
     * 导出时在excel中每个列的高度 单位为字符
     */
    double height() default 14;

    /**
     * 导出时在excel中每个列的宽 单位为字符
     */
    double width() default 16;

    /**
     * 文字后缀，如"%"，那数值90变成90%
     */
    String suffix() default "";

    /**
     * 当值为空时，字段的默认值
     */
    String defaultValue() default "";

    /**
     * 是否导出数据，应对需求：有些情况需要保持为空,希望用户填写这一列。
     */
    boolean isExport() default true;

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     */
    Type type() default Type.ALL;

    enum Type {
        /**
         * 导入导出
         */
        ALL(0),
        /**
         * 导出
         */
        EXPORT(1),
        /**
         * 导入
         */
        IMPORT(2);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
