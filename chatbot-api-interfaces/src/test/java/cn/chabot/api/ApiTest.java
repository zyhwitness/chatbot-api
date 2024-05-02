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

    /**
     * 使用 Apache HttpClient 来向 ZSXQ API 发送 HTTP GET 请求，以获取指定群组中的未回答的问题
     *
     * @throws IOException
     */
    @Test
    public void queryUnansweredQuestion() throws IOException {

        //使用 HttpClientBuilder 创建了一个 CloseableHttpClient 的实例来执行 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //创建了一个 HTTP GET 请求，目标是指定的 URL，即 ZSXQ API 的端点，用于从特定群组中获取未回答的问题。查询参数包括 scope，指定只检索未回答的问题，以及 count，指定要检索的问题数量
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28888818488211/topics?scope=unanswered_questions&count=20");

        //向请求头部添加必要的 cookie。这些 cookie 可能包含了 ZSXQ API 需要的认证和会话信息，用于认证和授权请求
        get.addHeader("cookie", "zsxq_access_token=1A07C22D-73DB-3A1B-1FB2-81FFFDBAC6EE_CD0467AD9CC5206D; zsxqsessionid=15515d3bc20a9d6446dd08ccbab2237a; abtest_env=product");

        //将请求的内容类型设置为 JSON，并使用 UTF-8 编码
        get.addHeader("content-type", "application/json; charset=UTF-8");

        //使用 httpClient 执行 HTTP GET 请求，并捕获响应
        CloseableHttpResponse response = httpClient.execute(get);

        //检查响应的状态码是否为 200（OK）。如果是，就将响应实体转换为字符串，并打印到控制台。否则打印状态码
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String rsp = EntityUtils.toString(response.getEntity());
            System.out.println(rsp);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    /**
     * 使用 Apache HttpClient 发送了一个 HTTP POST 请求到 ZSXQ API，来回答一个指定话题的问题
     *
     * @throws IOException
     */
    @Test
    public void answer() throws IOException {

        //创建了一个可关闭的 HttpClient 实例，用来执行 HTTP 请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //创建了一个 HTTP POST 请求，目标 URL 是 ZSXQ API 中指定话题的问题回答接口
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/2855224852484481/answer");

        //向请求头添加了必要的 cookie，其中包含了认证和会话信息
        post.addHeader("cookie", "zsxq_access_token=1A07C22D-73DB-3A1B-1FB2-81FFFDBAC6EE_CD0467AD9CC5206D; zsxqsessionid=15515d3bc20a9d6446dd08ccbab2237a; abtest_env=product");

        //设置了请求内容的类型为 JSON，并指定了 UTF-8 编码
        post.addHeader("content-type", "application/json; charset=UTF-8");

        //定义了请求的 JSON 参数，其中包含了回答的文本内容、图片 ID 和是否被禁言的信息
        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我真的不知道呢。。。\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        //创建了一个 StringEntity 对象，用于将 JSON 参数设置到 POST 请求的实体中
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        //执行了 HTTP POST 请求，并获取了响应
        CloseableHttpResponse response = httpClient.execute(post);

        //检查了响应的状态码，如果状态码是 200（OK），则将响应内容打印到控制台，否则打印状态码
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String rsp = EntityUtils.toString(response.getEntity());
            System.out.println(rsp);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");

        post.addHeader("Content-Type","application/json");
        post.addHeader("Authorization","Bearer sess-9QaCVzlhqrUtEKkfaai3OpVyeSPyeMeseFGq5WPw");

        String paramJson = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"写一个冒泡排序\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";

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



