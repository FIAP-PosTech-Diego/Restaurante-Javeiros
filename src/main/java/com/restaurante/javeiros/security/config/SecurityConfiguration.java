package com.restaurante.javeiros.security.config;

import com.restaurante.javeiros.security.authentication.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/user/login",
            "/user",
            "/user/get-all",
            "/h2-console",
            "/h2-console/",
            "/h2-console/stylesheet.css",
            "/h2-console/login.jsp",
            "/h2-console/background.gif",
            "/favicon.ico",
            "/h2-console/login.do",
            "/h2-console/tables.do",
            "/h2-console/help.jsp",
            "/h2-console/header.jsp",
            "/h2-console/query.jsp",
            "/h2-console/test.do",
            "/h2-console/icon_disconnect.gif",
            "/h2-console/icon_line.gif",
            "/h2-console/icon_commit.gif",
            "/h2-console/icon_stop.gif",
            "/h2-console/icon_run.gif",
            "/h2-console/tree.js",
            "/h2-console/icon_help.gif",
            "/h2-console/icon_history.gif",
            "/h2-console/icon_rollback.gif",
            "/h2-console/icon_run_selected.gif",

            "/swagger-ui/index.html",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/v3/api-docs/swagger-config",
            "/v3/api-docs"
    };

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/user/update-user",
            "/user/update-password"
    };

    public static final String [] ENDPOINTS_CUSTOMER = {
            "/user/test/customer"
    };

    public static final String [] ENDPOINTS_ADMIN = {
            "/user/test/administrator"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable
                )
                .authorizeHttpRequests((authz) -> authz
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                    .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                    .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                    .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                    .anyRequest().denyAll()
                )
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}