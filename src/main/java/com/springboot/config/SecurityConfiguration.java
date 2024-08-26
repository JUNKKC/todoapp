package com.springboot.config;

import com.springboot.auth.filter.JwtAuthenticationFilter;
import com.springboot.auth.jwt.JwtTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

  public SecurityConfiguration(JwtTokenizer jwtTokenizer) {
    this.jwtTokenizer = jwtTokenizer;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .headers().frameOptions().sameOrigin() // (1)
        .and()
        .csrf().disable()        // (2)
        .cors(withDefaults())    // (3)
        .formLogin().disable()   // (4)
        .httpBasic().disable()   // (5)
        .apply(new CustomFilterConfigurer())   // (6)
        .and()
        .authorizeHttpRequests(authorize -> authorize
            .anyRequest().permitAll()          // (7)
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

  public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {  // (13)
    @Override
    public void configure(HttpSecurity builder) throws Exception {  // (14)
      AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);  // (15)

      JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);  // (16)
      jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");          // (17)

      builder.addFilter(jwtAuthenticationFilter);  // (18)
    }
  }
}