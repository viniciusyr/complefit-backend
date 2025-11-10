package com.complefit.complefit.exercise.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationProperties(prefix = "exercisedb")
public class ExerciseDbConfig {

    private String apiUrl = "https://exercisedb.p.rapidapi.com";
    private String apiKey;
    private String apiHost = "exercisedb.p.rapidapi.com";

    @Bean
    public WebClient exerciseDbWebClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("X-RapidAPI-Key", apiKey != null ? apiKey : "")
                .defaultHeader("X-RapidAPI-Host", apiHost)
                .build();
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiHost() {
        return apiHost;
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
