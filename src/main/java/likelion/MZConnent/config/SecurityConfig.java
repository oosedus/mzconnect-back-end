package likelion.MZConnent.config;

import likelion.MZConnent.jwt.JwtAccessDeniedHandler;
import likelion.MZConnent.jwt.JwtAuthenticationEntryPoint;
import likelion.MZConnent.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtFilter jwtFilter;

    /**
     * 권한별 URI
     */

    // 관리자 권한
    private final String[] adminUrl = {"/admin/**"};

    // 인증된 사용자만 접근 가능한 URI (permitAllUrl과 겹치는 경우에만 추가 -> 미인증 사용자가 접근하는 URI는 모두 인가가 필요하게 설정되어 있어 추가할 필요 없음)
    private final String[] authenticatedUrl = {
            "/api/cultures/interest"
    };

    // 아무나 접근 가능한 URI
    private final String[] permitAllUrl = {"/error",
            "/api/auth/login", // 회원
            "/api/categories/culture", "/api/cultures", "/api/cultures/**", // 문화
            "/api/reviews", // 후기
            "/api/categories/region", "/api/clubs/list",
            "/api/main",
            "/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**", //swagger
            "/api/test"
    };

    // 인증되지 않은 사용자만 접근 가능한 URI
    private final String[] anonymousUrl = {
            "/api/auth/signup"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(handle -> handle
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth // 접근 uri 권한 관리
                        .requestMatchers(adminUrl).hasAnyRole("ADMIN")
                        .requestMatchers(authenticatedUrl).authenticated()
                        .requestMatchers(permitAllUrl).permitAll()
                        .requestMatchers(anonymousUrl).anonymous()
                        .anyRequest().authenticated() // 이 외의 url은 인증받은 사용자만 접근 가능
                );
        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // frontend url
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() { //비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
}
