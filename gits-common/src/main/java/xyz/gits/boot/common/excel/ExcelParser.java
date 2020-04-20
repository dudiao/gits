package xyz.gits.boot.common.excel;

import cn.hutool.core.util.ReflectUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Excel 解析器
 *
 * @author songyinyin
 * @date 2019/12/6 16:18
 */
public class ExcelParser<T> {

    /**
     * 导出类型
     */
    protected Excel.Type type;

    /**
     * 工作表名称
     */
    protected String sheetName;

    /**
     * 工作薄对象
     */
    protected Workbook wb;

    /**
     * 工作表对象
     */
    protected Sheet sheet;

    /**
     * 导入导出数据列表
     */
    protected List<T> list;

    /**
     * 注解列表
     */
    protected List<Field> fields;

    /**
     * 实体对象
     */
    protected Class<T> clazz;

    protected void init(List<T> list, String sheetName, Excel.Type type) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        createExcelField();
        createWorkbook();
    }

    /**
     * 得到所有定义字段
     */
    protected void createExcelField() {
        this.fields = new ArrayList<>();
        Field[] fields = ReflectUtil.getFields(clazz);
        List<Field> tempFields = Arrays.asList(fields);

        for (Field field : tempFields) {
            Excel attr = field.getAnnotation(Excel.class);
            if (attr != null && (attr.type() == Excel.Type.ALL || attr.type() == type)) {
                this.fields.add(field);
            }
        }
    }

    /**
     * 创建一个工作簿
     */
    protected void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
    }

}
