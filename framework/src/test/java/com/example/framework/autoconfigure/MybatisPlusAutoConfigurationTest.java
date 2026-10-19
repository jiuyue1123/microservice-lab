package com.example.framework.autoconfigure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class MybatisPlusAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MybatisPlusAutoConfiguration.class));

    @Test
    void registersDefaultInterceptorsAndMetaObjectHandler() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(MybatisPlusInterceptor.class);
            assertThat(context).hasSingleBean(MetaObjectHandler.class);
        });
    }
}
