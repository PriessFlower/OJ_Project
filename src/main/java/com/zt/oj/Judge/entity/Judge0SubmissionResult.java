package com.zt.oj.Judge.entity;// Judge0SubmissionResult.java
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Judge0SubmissionResult {
    @JsonProperty("stdout")
    private String stdout;       // 程序输出
    @JsonProperty("stderr")
    private String stderr;       // 错误输出
    @JsonProperty("time")
    private String time;         // 时间开销
    @JsonProperty("memory")
    private String memory;       // 内存开销
    @JsonProperty("token")
    private String token;
    @JsonProperty("message")
    private String message;
    @JsonProperty("compile_output")
    private String compileOutput; // 编译错误信息
    @JsonProperty("status")
    private Status status;       // 执行状态

    @Data
    public static class Status {
        @JsonProperty("id")
        private int id;          // 状态ID (3=执行成功, 6=编译错误等)
        @JsonProperty("description")
        private String description;
    }
}