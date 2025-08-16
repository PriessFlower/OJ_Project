package com.zt.oj.model.dto.submit;

import com.zt.oj.common.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@Data
public class SubmitQueryRequest extends PageRequest implements Serializable {

    private Long questionId;

    private Long userId;

    private Integer status;

    private String language;
}
