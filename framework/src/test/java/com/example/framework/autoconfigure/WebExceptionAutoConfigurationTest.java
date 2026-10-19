package com.example.framework.autoconfigure;

import com.example.framework.web.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class WebExceptionAutoConfigurationTest {

    private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(WebExceptionAutoConfiguration.class));

    @Test
    void registersGlobalExceptionHandler() {
        contextRunner.run(context ->
                assertThat(context).hasSingleBean(GlobalExceptionHandler.class));
    }
}
