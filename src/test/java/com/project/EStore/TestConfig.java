package com.project.EStore;

import com.project.EStore.config.CloudinaryConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public CloudinaryConfig config(){
        return new CloudinaryConfig();
    }
}
