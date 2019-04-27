package yuqiao.housesearch.form;

import lombok.Data;

@Data
public class BaseForm {
    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页显示的数目
     */
    private Integer pageSize = 10;
}
