package cn.chabot.api;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @Description: Test
 * @Author: iWitness
 * @Date: 2024/4/24 10:39
 * @Version 1.0
 */
public class ApiTest {

    @Test
    public void queryUnansweredQuestion() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28888818488211/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "zsxq_access_token=1A07C22D-73DB-3A1B-1FB2-81FFFDBAC6EE_CD0467AD9CC5206D; zsxqsessionid=15515d3bc20a9d6446dd08ccbab2237a; abtest_env=product");

        get.addHeader("content-type", "application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String rsp = EntityUtils.toString(response.getEntity());
            System.out.println(rsp);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/1522451515228822/answer");

        post.addHeader("cookie", "zsxq_access_token=1A07C22D-73DB-3A1B-1FB2-81FFFDBAC6EE_CD0467AD9CC5206D; zsxqsessionid=15515d3bc20a9d6446dd08ccbab2237a; abtest_env=product");

        post.addHeader("content-type", "application/json; charset=UTF-8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"自己去查\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String rsp = EntityUtils.toString(response.getEntity());
            System.out.println(rsp);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}



