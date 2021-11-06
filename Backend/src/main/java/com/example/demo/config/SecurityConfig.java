package com.example.demo.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.util.Constraints;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.cors.allow.origins}")
    private String corsAllowOrigin;

    @Value("${spring.security.cookie.domain}")
    private String cookieDomain;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                anyRequest().permitAll().
                and().
             httpBasic().
                 disable().
             sessionManagement().
                 sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                 and().
             cors().
                configurationSource(corsFilter()).and().
             csrf().
                 requireCsrfProtectionMatcher(new RequestMatcher() {
                     @Override
                     public boolean matches(HttpServletRequest request) {
                         String uri = request.getRequestURI();
                         if( uri.startsWith("/api/v1/auth/signin") || uri.startsWith("/api/endpoint")) {
                             return false;
                         } else {
                             return true;
                         }
                     }
                 }).
                 csrfTokenRepository(csrfTokenRepository());
    }

    private CookieCsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository retVal =  CookieCsrfTokenRepository.withHttpOnlyFalse();
        retVal.setHeaderName(Constraints.COOKIE_CSRF_KEY);
        retVal.setCookieName(Constraints.COOKIE_CSRF_KEY);
        retVal.setCookieDomain(cookieDomain);
        retVal.setCookiePath("/");
        return retVal;
    }

    private CorsConfigurationSource corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of(
                corsAllowOrigin));
        configuration.setAllowedHeaders(List.of(
                "Access-Control-Allow-Headers",
                "Access-Control-Allow-Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "Content-Type",
                "Accept-Language"));
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "OPTIONS"));
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication();
    }
}
