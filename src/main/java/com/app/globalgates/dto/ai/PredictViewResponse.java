package com.app.globalgates.dto.ai;

import lombok.Data;

@Data
public class PredictViewResponse {
    private int viewCount;
    private int maxViewCount;
    private int minViewCount;
    private String content;
}
