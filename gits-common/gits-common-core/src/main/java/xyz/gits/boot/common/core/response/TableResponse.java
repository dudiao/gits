package xyz.gits.boot.common.core.response;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 列表返回结果
 *
 * @author songyinyin
 * @date 2020/3/14 下午 09:14
 */
@Data
public class TableResponse<T> extends BaseResponse {

    TableData<T> data;

    public TableResponse(long total, List<T> rows) {
        this.data = new TableData<T>(total, rows);
    }

    public static <T> TableResponse<T> success(long total, List rows) {
        return new TableResponse<T>(total, rows);
    }

    public class TableData<T> {
        long total;
        List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public TableData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}
