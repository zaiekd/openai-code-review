package org.zaiekd.middleware.sdk.infrastructure.weixin;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaiekd.middleware.sdk.infrastructure.weixin.dto.TemplateMessageDTO;
import org.zaiekd.middleware.sdk.types.utils.WXAccessTokenUtils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

/**
 * @author lhz
 * @version 1.0
 * @create 2/16/25 12:26 AM
 */
public class Weixin {

    private final Logger logger = LoggerFactory.getLogger(Weixin.class);

    private final String appId;

    private final String secret;

    private final String touser;

    private final String templateId;

    public Weixin(String appId, String secret, String touser, String templateId) {
        this.appId = appId;
        this.secret = secret;
        this.touser = touser;
        this.templateId = templateId;
    }

    public void sendTemplateMessage(String logUrl, Map<String, Map<String, String>> data) throws Exception {

        String accessToken = WXAccessTokenUtils.getAccessToken(appId, secret);

        TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO(touser, templateId);
        templateMessageDTO.setUrl(logUrl);
        templateMessageDTO.setData(data);

        URL url = new URL(String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", accessToken));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = JSON.toJSONString(templateMessageDTO).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8.name())) {
            String response = scanner.useDelimiter("\\A").next();
            logger.info("openai-code-review weixin template message! {}", response);
        }
        
    }
}
