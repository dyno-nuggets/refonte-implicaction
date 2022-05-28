package com.dynonuggets.refonteimplicaction.config;

import com.dynonuggets.refonteimplicaction.model.RoleEnum;
import com.dynonuggets.refonteimplicaction.security.JwtAuthenticationFilter;
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

import static com.dynonuggets.refonteimplicaction.utils.ApiUrls.*;

@EnableWebSecurity
@AllArgsConstructor
@EnableScheduling
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // api
            "/api/auth/signup",
            "/api/auth/login",
            "/api/auth/refresh/token",
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
    };

    // Toutes les routes front doivent être autorisées en back car c'est angular qui en gère l'accès
    private static final String[] ANGULAR_WHITELIST = {
            "/",
            "/entreprise/**",
            "/users/**",
            "/jobs/**",
            "/board/**",
            "/admin/**",
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

    private static final String[] ADMIN_PROTECTEDS = {
            "/api/auth/accountVerification/**"
    };

    private static final String[] PREMIUM_PROTECTEDS = {
            APPLY_BASE_URI + "/**",
    };

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().ignoringAntMatchers(AUTH_WHITELIST)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(ANGULAR_WHITELIST).permitAll()
                .antMatchers(ADMIN_PROTECTEDS).hasRole(RoleEnum.ADMIN.name())
                .antMatchers(PREMIUM_PROTECTEDS).hasRole(RoleEnum.PREMIUM.name())
                .anyRequest()
                .authenticated();
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
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
