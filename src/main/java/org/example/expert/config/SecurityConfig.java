package org.example.expert.config;


import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean//springsecurity에서 제공해주니 따로 만들필요 없다.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //.anyRequest().authenticated() <- 기본적으로 무조건 들어가고,
    // 그 외에 특별한 동작을 하는 url path만 따로 정의를 해주면 된다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, SecurityContextHolderAwareRequestFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth// jwtFilter에서 했던 url 허용을 authrizeHttpRequests여기서 해준다.
                        .requestMatchers(request -> request.getRequestURI().startsWith("/auth")).permitAll()
                        .requestMatchers(request -> request.getRequestURI().startsWith("/admin")).hasAuthority(UserRole.Authority.ADMIN)
                        .anyRequest().authenticated()//이 코드가 securityContext에 Authentication 객체가 set된 요청만 통과시키겠다.
                )
                .build();
    }
}
