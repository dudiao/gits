package xyz.gits.boot.common.core.excel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Excel导入导出工具类，使用示例：
 * <pre>
 * List<UserExcelVO> userList = getUserExcelVOs();
 * ExcelUtil<UserExcelVO> util = new ExcelUtil<>(UserExcelVO.class);
 * String excelPath = util.exportForExcel(userList, "用户数据");
 * </pre>
 *
 * @author songyinyin
 * @date 2019/12/6 15:14
 */
@Slf4j
@Data
public class ExcelUtil<T> extends ExcelParser<T> {

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    private String exportPath;

    /**
     * 有参构造
     *
     * @param clazz      导入导出实体class
     * @param exportPath 导出路径
     */
    public ExcelUtil(Class<T> clazz, String exportPath) {
        this.clazz = clazz;
        this.exportPath = exportPath;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 导出Excel文件的路径，如：/opt/platform/459b6e4c-用户操作日志.xlsx
     */
    public String exportForExcel(List<T> list, String sheetName) {
        this.init(list, sheetName, Excel.Type.EXPORT);
        return exportForExcel();
    }

    /**
     * Excel导出逻辑
     */
    private String exportForExcel() {
        String filename;
        OutputStream out = null;
        try {
            // 取出一共有多少个sheet.
            double sheetNo = Math.ceil(list.size() / sheetSize);
            for (int index = 0; index <= sheetNo; index++) {
                createSheet(sheetNo, index);
                // 填充excel列名
                fillExcelTitle();
                fillExcelData(index);
            }
            filename = encodingFilename(sheetName);
            out = new FileOutputStream(filename);
            wb.write(out);
        } catch (Exception e) {
            log.error("导出Excel异常：{}", e.getMessage());
            throw new ExcelException("导出Excel异常", e);
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return filename;
    }

    /**
     * 编码文件名，修复并补全文件全路径，并将文件名加上8位uid前缀，形如：
     * <p>
     *     /opt/platform/459b6e4c-用户操作日志.xlsx
     * </p>
     *
     * @return 文件全路径
     */
    public String encodingFilename(String filename) {
        // 修复路径
        String normalizeExportPath = FileUtil.normalize(exportPath);
        String path = normalizeExportPath.endsWith("/") ? normalizeExportPath : normalizeExportPath + CharUtil.SLASH;
        return path + IdWorker.get32UUID().substring(0, 8) + "_" + filename + ".xlsx";
    }

    /**
     * 填充excel列名
     */
    private void fillExcelTitle() {
        Row row = sheet.createRow(0);
        // 写入各个字段的列头名称
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            Excel attr = field.getAnnotation(Excel.class);
            // 创建列
            Cell cell = row.createCell(i);
            // 设置列中写入内容为String类型
            cell.setCellType(CellType.STRING);
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Font font = wb.createFont();
            // 粗体显示
            font.setBold(true);
            // 选择需要用到的字体格式
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
            // 设置列宽
            sheet.setColumnWidth(i, (int) ((attr.width() + 0.72) * 256));
            row.setHeight((short) (attr.height() * 20));

            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setWrapText(true);
            cell.setCellStyle(cellStyle);

            // 写入列名
            cell.setCellValue(attr.name());
        }
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     */
    private void fillExcelData(int index) {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());

        // 写入各条记录,每条记录对应excel表中的一行
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        for (int i = startNo; i < endNo; i++) {
            Row row = sheet.createRow(i + 1 - startNo);
            // 得到导出对象.
            T vo = (T) list.get(i);
            for (int j = 0; j < fields.size(); j++) {
                // 获得field.
                Field field = fields.get(j);
                Excel attr = field.getAnnotation(Excel.class);
                try {
                    // 设置行高
                    row.setHeight((short) (attr.height() * 20));
                    // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                    if (!attr.isExport()) {
                        continue;
                    }
                    // 创建cell
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(cs);
                    if (vo == null) {
                        // 如果数据存在就填入,不存在填入空格.
                        cell.setCellValue("");
                        continue;
                    }

                    // 用于读取对象中的属性
                    Object value = ReflectUtil.getFieldValue(vo, field);
                    //时间转化
                    String date = attr.dateFormat();
                    //读取内容转表达式 (如: 0=男,1=女,2=未知)
                    String readConverterExp = attr.readConverterExp();
                    //字典转换
                    String converteDict = attr.converteDict();
                    /**
                     *
                     * 转换优先级 date > 内容转表达式 > 字典转换
                     *
                     */
                    // 日期格式不为空和内容都不为空
                    if (StrUtil.isNotEmpty(date) && ObjectUtil.isNotNull(value)) {
                        if (value instanceof Date) {
                            cell.setCellValue(DateUtil.format((Date) value, date));
                        } else if (value instanceof LocalDateTime) {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(date);
                            cell.setCellValue(dateTimeFormatter.format((LocalDateTime) value));
                        } else {
                            cell.setCellValue(value.toString());
                        }
                    }
                    // 读取内容转表达式不为空且内容都不为空
                    else if (StrUtil.isNotEmpty(readConverterExp) && ObjectUtil.isNotNull(value)) {
                        cell.setCellValue(convertByExp(String.valueOf(value), readConverterExp));
                    }
                    // 字典转换不为空且内容都不为空
                    else if (StrUtil.isNotEmpty(converteDict) && ObjectUtil.isNotNull(value)) {
                        cell.setCellValue(convertDictByExp(String.valueOf(value), converteDict));
                    }
                    // 如果数据存在就填入,不存在填入空格.
                    else {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(ObjectUtil.isNull(value) ? attr.defaultValue() : value + attr.suffix());
                    }
                } catch (Exception e) {
                    log.error("导出Excel失败", e);
                }
            }
        }
    }

    /**
     * 数据字典转换
     *
     * @param propertyValue 参数值
     * @param converterExp  注解中的表达式
     * @return 数据字典转换后的值
     */
    private String convertDictByExp(String propertyValue, String converterExp) {
        // TODO Excel: 字典转换
        return null;
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @return 解析后值
     */
    private String convertByExp(String propertyValue, String converterExp) {
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (itemArray[0].equals(propertyValue)) {
                return itemArray[1];
            }
        }
        return propertyValue;
    }

    /**
     * 创建工作表，第二个sheet的名字为：sheetName + index
     *
     * @param sheetNo sheet数量
     * @param index   序号
     */
    private void createSheet(double sheetNo, int index) {
        this.sheet = wb.createSheet();
        // 设置工作表的名称.
        if (sheetNo == 0) {
            wb.setSheetName(index, sheetName);
        } else {
            wb.setSheetName(index, sheetName + index);
        }
    }
}
