package yuqiao.housesearch.craw.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import yuqiao.housesearch.common.enums.ErrorCode;
import yuqiao.housesearch.common.execption.BizException;
import yuqiao.housesearch.config.SslSocketClient;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 浦希成
 * 2018-11-30
 * Okhttp工具类
 */
@Slf4j
public class OKHttpUtil {
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
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
            .sslSocketFactory(SslSocketClient.getSSLSocketFactory())
            .hostnameVerifier(SslSocketClient.getHostnameVerifier())
            .build();

    /**
     * get请求 同步
     *
     * @param url        url
     * @param headersMap 请求头
     * @return
     */
    public JSONObject getJson(String url, Map<String, String> headersMap) {
        if (StringUtils.isBlank(url)) {
            throw new BizException(ErrorCode.PROPERTY_NULL_ERROR);
        }
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
     * get请求 同步 得到文件流
     *
     * @param url        url
     * @param headersMap 请求头
     * @return
     */
    public InputStream getFileStream(String url, Map<String, String> headersMap) {
        if (StringUtils.isBlank(url)) {
            throw new BizException(ErrorCode.PROPERTY_NULL_ERROR);
        }
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
            if (response.isSuccessful()&&response.body()!=null) {
                return response.body().byteStream();
            }
        } catch (Exception e) {
            log.error("Get error：{}", e);

        }
        return null;
    }
    /**
     * get请求获取网页 同步
     *
     * @param url        url
     * @param headersMap 请求头
     * @return
     */
    public String getHtml(String url, Map<String, String> headersMap) {
        if (StringUtils.isBlank(url)) {
            throw new BizException(ErrorCode.PROPERTY_NULL_ERROR);
        }
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
     *
     * @param url
     * @return
     */
    public JSONObject getJson(String url) {
        return getJson(url, null);
    }
    /**
     * get 不带header
     *
     * @param url
     * @return
     */
    public InputStream getFileStream(String url) {
        return getFileStream(url, null);
    }

    /**
     * get 网页数据 不带header
     *
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
    public JSONObject post(String url, Map<String, String> headersMap, Map<String, String> dataMap) {
        if (StringUtils.isBlank(url)) {
            throw new BizException(ErrorCode.PROPERTY_NULL_ERROR);
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        if (headersMap != null) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        FormBody.Builder params = new FormBody.Builder();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            params.add(entry.getKey(), entry.getValue());

        }
        requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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

}
