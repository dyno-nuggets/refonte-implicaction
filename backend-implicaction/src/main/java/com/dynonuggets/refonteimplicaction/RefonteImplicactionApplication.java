package com.dynonuggets.refonteimplicaction;

import com.dynonuggets.refonteimplicaction.core.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableAsync
@SpringBootApplication
@Import(SwaggerConfiguration.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class RefonteImplicactionApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RefonteImplicactionApplication.class, args);
    }

}
