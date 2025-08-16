package com.zt.oj.Judge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zt.oj.Judge.entity.CaseResult;
import com.zt.oj.Judge.entity.Judge0SubmissionResult;
import com.zt.oj.Judge.entity.TestCase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Judge0Client {
    private static final String JUDGE0_URL = "http://192.168.155.73:2358";
    private final ObjectMapper mapper = new ObjectMapper();

    //执行代码
    public List<CaseResult> execute(String userCode, List<TestCase> testCases) {
        if (testCases == null || testCases.isEmpty()) {
            return Collections.emptyList();
        }

        try {
            // 1. 提交代码
            String token = submitCode(userCode, testCases.get(0)).getToken();

            // 2. 轮询结果（等待执行完成）
            Judge0SubmissionResult result = pollSubmissionResult(token);

            // 3. 转换为CaseResult
            return convertToCaseResult(result);
        } catch (Exception e) {
            log.error("判题失败: {}", e.getMessage(), e);
            throw new RuntimeException("判题失败", e);
        }
    }

    private SubmissionToken submitCode(String code, TestCase testCase) throws IOException, ParseException {
        HttpPost request = new HttpPost(JUDGE0_URL + "/submissions");
        request.setHeader("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source_code", code);
        requestBody.put("language_id", 62); // C语言
        requestBody.put("stdin", "");
        requestBody.put("base64_encoded", true);

        String json = mapper.writeValueAsString(requestBody);
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return mapper.readValue(responseBody, SubmissionToken.class);
        }
    }

    //轮询查询结果
    private Judge0SubmissionResult pollSubmissionResult(String token) throws IOException, InterruptedException, ParseException {
        while (true) {
            HttpGet request = new HttpGet(JUDGE0_URL + "/submissions/" + token);
            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                String json = EntityUtils.toString(entity);
                EntityUtils.consume(entity);

                Judge0SubmissionResult result = mapper.readValue(json, Judge0SubmissionResult.class);

                // 如果状态不是 "In Queue"(1) 或 "Processing"(2)，则返回结果
                if (result.getStatus() != null && result.getStatus().getId() > 2) {
                    return result;
                }
            }
            Thread.sleep(1000); // 每隔1秒查询一次
        }
    }


    private List<CaseResult> convertToCaseResult(Judge0SubmissionResult result) {
        CaseResult caseResult = new CaseResult();
        caseResult.setActualOutput(result.getStdout());
        caseResult.setErrorMsg(result.getStderr());
        caseResult.setPassed(result.getStatus() != null && result.getStatus().getId() == 3); // 3=执行成功
        return List.of(caseResult);
    }

    @Data
    private static class SubmissionToken {
        private String token;
    }
}