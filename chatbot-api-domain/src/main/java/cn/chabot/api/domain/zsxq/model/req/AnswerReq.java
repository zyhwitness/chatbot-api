package cn.chabot.api.domain.zsxq.model.req;

/**
 * @Description: 请求问答接口信息
 * @Author: iWitness
 * @Date: 2024/4/30 10:03
 * @Version 1.0
 */
public class AnswerReq {

    private ReqData req_Data;

    public AnswerReq(ReqData req_Data) {
        this.req_Data = req_Data;
    }

    public ReqData getReq_Data() {
        return req_Data;
    }

    public void setReq_Data(ReqData req_Data) {
        this.req_Data = req_Data;
    }
}
