package com.glunah2001.ClinicSystemAPI.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> whitelistPaths = new ArrayList<>();
}
