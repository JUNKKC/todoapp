package com.springboot.config;

import com.springboot.auth.filter.JwtAuthenticationFilter;
import com.springboot.auth.filter.JwtVerificationFilter;
import com.springboot.auth.handler.MemberAuthenticationFailureHandler;
import com.springboot.auth.handler.MemberAuthenticationSuccessHandler;
import com.springboot.auth.jwt.JwtTokenizer;
import com.springboot.auth.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
  private final JwtTokenizer jwtTokenizer;
  private final CustomAuthorityUtils authorityUtils;

  public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                               CustomAuthorityUtils authorityUtils) {
    this.jwtTokenizer = jwtTokenizer;
    this.authorityUtils = authorityUtils;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .headers().frameOptions().sameOrigin()
        .and()
        .csrf().disable()
        .cors(withDefaults())  // CORS 설정 적용
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin().disable()
        .httpBasic().disable()
        .apply(new CustomFilterConfigurer())
        .and()
        .authorizeHttpRequests(authorize -> authorize
            .antMatchers(HttpMethod.POST, "/**").permitAll()         // (1)
            .antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER")  // (2)
            .antMatchers(HttpMethod.GET, "/members").hasRole("ADMIN")     // (3)
            .antMatchers(HttpMethod.GET, "/members/**").hasAnyRole("USER", "ADMIN")  // (4)
            .antMatchers(HttpMethod.DELETE, "/members/**").hasRole("USER")  // (5)
            .anyRequest().permitAll()
        );
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // (8)
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));   // 허용할 출처 설정
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));  // 허용할 메서드 설정
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 허용할 헤더 설정
    configuration.setAllowCredentials(true); // 인증 정보를 포함하도록 설정

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
    return source;
  }

  public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

      JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
      jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
      jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
      jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

      JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);

      builder
          .addFilter(jwtAuthenticationFilter)
          .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
  }
}