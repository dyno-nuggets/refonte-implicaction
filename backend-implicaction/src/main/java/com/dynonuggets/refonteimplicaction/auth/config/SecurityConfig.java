package com.dynonuggets.refonteimplicaction.auth.config;

import com.dynonuggets.refonteimplicaction.auth.filter.JwtAuthenticationFilter;
import com.dynonuggets.refonteimplicaction.user.domain.enums.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_BASE_URI;
import static com.dynonuggets.refonteimplicaction.core.util.ApiUrls.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@AllArgsConstructor
@EnableScheduling
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] NO_AUTHENTICATION_URIS = {
            AUTH_BASE_URI + "/**",
            POSTS_BASE_URI + GET_LATEST_POSTS_URI + "/**",
            JOBS_BASE_URI + GET_LATEST_JOBS_URI + "/**",
            JOBS_BASE_URI + VALIDATED_JOBS + "?**",

            // swagger
            "/v2/api-docs",

            // swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/assets/**"
    };

    private static final String[] CSRF_DISABLED_URIS = {
            AUTH_BASE_URI + "/**"
    };

    // Toutes les routes du front doivent être autorisées en back car c’est angular qui en gère l’accès
    private static final String[] FRONT_URIS = {
            "/",
            "/entreprise/**",
            "/auth/**",
            "/error",
            "/index.html",
            "/*.js",
            "/*.js.map",
            "/*.css",
            "/*.css.map",
            "/assets/img/*.png",
            "/assets/img/*.jpg",
            "/favicon.ico",
            "/**.ttf",
            "/**.woff"
    };

    private static final String[] ADMIN_RESTRICTED_URIS = {
    };

    private static final String[] PREMIUM_RESTRICTED_URIS = {
            APPLY_BASE_URI + "/**",
    };

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().ignoringAntMatchers(CSRF_DISABLED_URIS).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers(NO_AUTHENTICATION_URIS).permitAll()
                .antMatchers(FRONT_URIS).permitAll()
                .antMatchers(ADMIN_RESTRICTED_URIS).hasRole(RoleEnum.ADMIN.name())
                .antMatchers(PREMIUM_RESTRICTED_URIS).hasRole(RoleEnum.PREMIUM.name())
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS);

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
