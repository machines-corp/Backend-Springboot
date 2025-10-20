package com.mchscorp.integrajob.datapi.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedPromptDTO {
    private Map<String, List<String>> include;
    private Map<String, List<String>> exclude;
    private Integer salaryMin;
    private String currency;
}
