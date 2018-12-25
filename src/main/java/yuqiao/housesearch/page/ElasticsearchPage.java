package yuqiao.housesearch.page;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Z浦希成
 * ES分页信息
 */
@Data
public class ElasticsearchPage implements Serializable {

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页显示多少条
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private int total;

    /**
     * 本页的数据列表
     */
    private List<JSONObject> dataList;


    /**
     * 总页数
     */
    private int totalPage;

    public ElasticsearchPage(int pageNum, int pageSize, int total, List<JSONObject> dataList) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.dataList = dataList;
        this.totalPage = (this.total + pageSize - 1) / pageSize;
    }
    public ElasticsearchPage() {
        this.pageNum = 1;
        this.pageSize = 1;
        this.total = 0;
        this.dataList = Collections.emptyList();
        this.totalPage = 0;
    }






}