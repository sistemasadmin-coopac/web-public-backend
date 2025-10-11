package com.elsalvador.coopac.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "cors.allowed")
@Getter
@Setter
public class CorsProperties {

    private List<String> origins = new ArrayList<>();
}
