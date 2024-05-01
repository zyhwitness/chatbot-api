package cn.chabot.api.domain.zsxq.model.aggregates;

import cn.chabot.api.domain.zsxq.model.res.ResData;

/**
 * @Description: 未回答问题的聚合信息
 * @Author: iWitness
 * @Date: 2024/4/30 09:21
 * @Version 1.0
 */
public class UnAnsweredQuestionsAggregates {

    private boolean succeeded;

    private ResData resp_data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public ResData getResp_data() {
        return resp_data;
    }

    public void setResp_data(ResData resp_data) {
        this.resp_data = resp_data;
    }
}
