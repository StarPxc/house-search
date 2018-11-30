package yuqiao.housesearch.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.BufferedSink;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.client.methods.RequestBuilder;
import org.yaml.snakeyaml.util.UriEncoder;
import yuqiao.housesearch.common.constants.Constants;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 浦希成
 * 2018-11-30
 * Okhttp工具类
 */
@Slf4j
public class OKHttpUtil {
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            //管理cookie
            .cookieJar(new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<>();
                }
            })
            .build();

    /**
     * get请求 同步
     *
     * @param url        url
     * @param headersMap 请求头
     * @return
     */
    public JSONObject get(String url, Map<String, String> headersMap) {
        Request.Builder requestBuilder = new Request.Builder()
                .get()
                .url(url);
        if (headersMap != null) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return getJSONObjectByResponse(requestBuilder);
    }
    /**
     * get请求获取网页 同步
     *
     * @param url        url
     * @param headersMap 请求头
     * @return
     */
    public String getHtml(String url, Map<String, String> headersMap) {
        Request.Builder requestBuilder = new Request.Builder()
                .get()
                .url(url);
        if (headersMap != null) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            Response response = client.newCall(requestBuilder.build()).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error("Get error：{}", e);

        }
        return "";
    }


    /**
     * get 不带header
     * @param url
     * @return
     */
    public JSONObject get(String url) {
        return get(url, null);
    }
    /**
     * get 网页数据 不带header
     * @param url
     * @return
     */
    public String getHtml(String url) {
        return getHtml(url, null);
    }
    /**
     * get请求 同步
     *
     * @param url        url
     * @param headersMap 请求头
     * @return
     */
    public JSONObject post(String url, Map<String, String> headersMap,Map<String, String> dataMap) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        if (headersMap != null) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        FormBody.Builder params = new FormBody.Builder();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            params.add(entry.getKey(),entry.getValue());

        }
        requestBuilder.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
        requestBuilder.post(params.build());
        return getJSONObjectByResponse(requestBuilder);
    }

    private JSONObject getJSONObjectByResponse(Request.Builder requestBuilder) {
        try {
            Response response = client.newCall(requestBuilder.build()).execute();
            if (response.isSuccessful()) {
                return JSONObject.parseObject(response.body().string());
            }
        } catch (Exception e) {
            log.error("Get error：{}", e);

        }
        return null;
    }


    public static void main(String[] args) throws IOException {
        OKHttpUtil util = new OKHttpUtil();
         HashMap newsEventMap = new HashMap<Object, Object>(){{
            put("username","152210702119");
            put("password","935377012pxc");

        }};
        System.out.println(util.post("http://guohe3.com/api/studentInfo",null,newsEventMap));
    }
}
