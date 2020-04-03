package com.mt.base.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class HttpClientUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    public static JSONObject get(String uri) {
        CloseableHttpClient client;
        CloseableHttpResponse response;
        try {
            HttpGet get = new HttpGet(uri);
            client = HttpClients.createDefault();
            response = client.execute(get);
            return getJsonResponse(response);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return new JSONObject();
        }
    }

    public static JSONObject postJSON(String uri, JSONObject data) {
        CloseableHttpClient client;
        CloseableHttpResponse response;
        try {
            HttpPost post = new HttpPost(uri);
            StringEntity entity = new StringEntity(data.toJSONString(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json;charset=utf-8");
            post.setEntity(entity);
            client = HttpClients.createDefault();
            response = client.execute(post);
            return getJsonResponse(response);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return new JSONObject();
        }
    }

    public static String postXml(String uri, String xml) {
        CloseableHttpClient client;
        CloseableHttpResponse response;
        try {
            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-Type", "text/xml;charset=utf-8");
            client = HttpClients.createDefault();
            StringEntity entity = new StringEntity(xml, "UTF-8");
            post.setEntity(entity);
            response = client.execute(post);
            LOGGER.info(response.toString());
            return getXmlResponse(response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private static JSONObject getJsonResponse(CloseableHttpResponse response) {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                byte[] resultBytes = EntityUtils.toByteArray(response.getEntity());
                String ret = new String(resultBytes, "UTF-8");
                return JSONObject.parseObject(ret);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                return new JSONObject();
            }
        } else {
            return new JSONObject();
        }
    }

    private static String getXmlResponse(CloseableHttpResponse response) {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                byte[] resultBytes = EntityUtils.toByteArray(response.getEntity());
                return new String(resultBytes, StandardCharsets.UTF_8);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }
}
