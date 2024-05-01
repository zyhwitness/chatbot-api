package cn.chabot.api.domain.zsxq.model.res;

import cn.chabot.api.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * @Description: TODO
 * @Author: iWitness
 * @Date: 2024/4/29 18:01
 * @Version 1.0
 */
public class ResData {

    private List<Topics> topics;

    public List<Topics> getTopics() {
        return topics;
    }

    public void setTopics(List<Topics> topics) {
        this.topics = topics;
    }
}
