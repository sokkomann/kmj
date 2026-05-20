package com.app.globalgates.controller.ai;

import com.app.globalgates.dto.ai.ChatBotResponse;
import com.app.globalgates.dto.ai.ImageDescribeResponse;
import com.app.globalgates.dto.ai.PredictTagResponse;
import com.app.globalgates.dto.ai.PredictViewResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/ai")
public class AIController {
    private final WebClient webClient;

    public AIController(@Value("${AI_FASTAPI_BASE_URL}") String fastapiBaseUrl) {
        this.webClient = WebClient.create(fastapiBaseUrl);
    }

    @PostMapping("/predict-tag")
    @ResponseBody
    public Mono<PredictTagResponse> aiPredictTag(@RequestBody Map<String, String> body){
        String contents = body.get("contents");
        log.info("contents: {}", contents);

        return webClient.post()
                .uri("/api/ai/predict-tag")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(PredictTagResponse.class); // 비동기로 결과를 받음
    }


    @PostMapping("/predict-view")
    @ResponseBody
    public Mono<PredictViewResponse> aiPredictView(@RequestBody Map<String, String> body){
        String contents = body.get("contents");
        log.info("contents: {}", contents);

        return webClient.post()
                .uri("/api/ai/predict-view")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(PredictViewResponse.class); // 비동기로 결과를 받음
    }


    @PostMapping("/image-describe")
    @ResponseBody
    public Mono<ImageDescribeResponse> aiImageDescribe(@RequestBody Map<String, String> body){
        log.info("이미지 설명 컨트롤러");

        return webClient.post()
                .uri("/api/ai/image-describe")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ImageDescribeResponse.class);
    }

    @PostMapping("/feed-chat")
    @ResponseBody
    public Mono<ChatBotResponse> aiChatBot(@RequestBody Map<String, String> body){
        String question = body.get("question");
        log.info("챳봇 들어옴. question: {}", question);

        return webClient.post()
                .uri("/api/ai/feed-chat")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(ChatBotResponse.class);  // 비동기로 결과를 받음
    }
}
