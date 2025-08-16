package com.zt.oj.model.dto.submit;

import lombok.Data;

@Data
public class SubmitAddRequest {

    private Long questionId;

    private String code;

    private String language;
}
