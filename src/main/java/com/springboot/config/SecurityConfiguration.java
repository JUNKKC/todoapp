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
  private final CustomAuthorityUtils authorityUtils; // 추가

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
        .cors(withDefaults())
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin().disable()
        .httpBasic().disable()
        .apply(new CustomFilterConfigurer())
        .and()
        .authorizeHttpRequests(authorize -> authorize
            .antMatchers(HttpMethod.POST, "/members").permitAll()         // (1) 추가
            .antMatchers(HttpMethod.PATCH, "/members/**").hasRole("USER")  // (2) 추가
            .antMatchers(HttpMethod.GET, "/members").hasRole("ADMIN")     // (3) 추가
            .antMatchers(HttpMethod.GET, "/members/**").hasAnyRole("USER", "ADMIN")  // (4) 추가
            .antMatchers(HttpMethod.DELETE, "/members/**").hasRole("USER")  // (5) 추가
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
    configuration.setAllowedOrigins(Arrays.asList("*"));   // (9)
    configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));  // (10)

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();   // (11)
    source.registerCorsConfiguration("/**", configuration);      // (12)
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

      JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);  // (2) 추가

      builder
          .addFilter(jwtAuthenticationFilter)
          .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);   // (3)추가
    }
  }
}