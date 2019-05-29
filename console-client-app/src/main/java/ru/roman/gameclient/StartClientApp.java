package ru.roman.gameclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackageClasses = {StartClientApp.class})
public class StartClientApp {
    private static final Logger log = LoggerFactory.getLogger(StartClientApp.class);

    @Value("${clientApp.config.gameServerBaseUrl}")
    private String gameServerBaseUrl;

    public static void main(String[] args) {
        new SpringApplicationBuilder(StartClientApp.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        log.info("Game server base URL: {}", gameServerBaseUrl);
        return builder
                .rootUri(gameServerBaseUrl)
                .build();
    }
}
