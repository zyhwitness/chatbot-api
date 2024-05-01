package cn.chabot.api.domain.zsxq.service;

import cn.chabot.api.domain.zsxq.IZsxqApi;
import cn.chabot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.chabot.api.domain.zsxq.model.req.AnswerReq;
import cn.chabot.api.domain.zsxq.model.req.ReqData;
import cn.chabot.api.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: iWitness
 * @Date: 2024/4/30 09:42
 * @Version 1.0
 */
@Service
public class ZsxqApi implements IZsxqApi {

    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {

        //使用 HttpClientBuilder 创建了一个 CloseableHttpClient 的实例来执行 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //创建了一个 HTTP GET 请求，目标是指定的 URL，即 ZSXQ API 的端点，用于从特定群组中获取未回答的问题。查询参数包括 scope，指定只检索未回答的问题，以及 count，指定要检索的问题数量
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");

        //向请求头部添加必要的 cookie。这些 cookie 可能包含了 ZSXQ API 需要的认证和会话信息，用于认证和授权请求
        get.addHeader("cookie", cookie);

        //将请求的内容类型设置为 JSON，并使用 UTF-8 编码
        get.addHeader("content-type", "application/json; charset=UTF-8");

        //使用 httpClient 执行 HTTP GET 请求，并捕获响应
        CloseableHttpResponse response = httpClient.execute(get);

        //检查响应的状态码是否为 200（OK）
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());

            logger.info("拉取提问数据。groupId：{} jsonStr：{}", groupId, jsonStr);

            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }

    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {

        //创建了一个可关闭的 HttpClient 实例，用来执行 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //创建了一个 HTTP POST 请求，目标 URL 是 ZSXQ API 中指定话题的问题回答接口
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");

        //向请求头添加了必要的 cookie，其中包含了认证和会话信息
        post.addHeader("cookie", cookie);

        post.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");

        //设置了请求内容的类型为 JSON，并指定了 UTF-8 编码
        post.addHeader("content-type", "application/json; charset=UTF-8");

        //定义了请求的 JSON 参数，其中包含了回答的文本内容、图片 ID 和是否被禁言的信息
        /*
        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我真的不知道呢。。。\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";
         */

        AnswerReq answerReq = new AnswerReq(new ReqData(text, silenced));
        String paramJson = JSONObject.fromObject(answerReq).toString();

        //创建了一个 StringEntity 对象，用于将 JSON 参数设置到 POST 请求的实体中
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        //执行了 HTTP POST 请求，并获取了响应
        CloseableHttpResponse response = httpClient.execute(post);

        //检查响应的状态码
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());

            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);

            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }

    }
}
