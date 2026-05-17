package com.app.globalgates.dto.ai;

import lombok.Data;

import java.util.List;

@Data
public class PredictTagResponse {
    private List<String> tags;
}