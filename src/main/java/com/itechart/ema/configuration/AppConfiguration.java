package com.itechart.ema.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class AppConfiguration {

    private Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt {

        @NotBlank
        private String base64Secret;

        private long accessTokenValidity;

        private long refreshTokenValidity;

    }

}
