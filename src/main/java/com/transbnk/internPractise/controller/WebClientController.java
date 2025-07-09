package com.transbnk.internPractise.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/webcli")
public class WebClientController {

    private final WebClient webClient;

    public WebClientController(WebClient.Builder builder){
        this.webClient = builder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    @GetMapping("/posts")
    public Flux<String> getPosts() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(String.class);
    }

}
