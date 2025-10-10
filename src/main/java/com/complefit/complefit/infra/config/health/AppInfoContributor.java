package com.complefit.complefit.infra.config.health;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("app", Map.of(
                "name", "CompleFit",
                "version", "1.0.0",
                "description", "Workout tracking and training management API"
        ));
    }
}
