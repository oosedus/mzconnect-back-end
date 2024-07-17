package likelion.MZConnent.config;

import likelion.MZConnent.jwt.JwtAccessDeniedHandler;
import likelion.MZConnent.jwt.JwtAuthenticationEntryPoint;
import likelion.MZConnent.jwt.JwtProperties;
import likelion.MZConnent.jwt.blacklist.AccessTokenBlackList;
import likelion.MZConnent.jwt.token.TokenProvider;
import likelion.MZConnent.jwt.token.refreshToken.RefreshTokenList;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {
    private final AccessTokenBlackList accessTokenBlackList;
    private final RefreshTokenList refreshTokenList;

    @Bean
    public TokenProvider tokenProvider(JwtProperties jwtProperties) {
        return new TokenProvider(jwtProperties.getSecret(), jwtProperties.getAccessTokenValidityInSeconds(), jwtProperties.getRefreshTokenValidityInSeconds(), accessTokenBlackList, refreshTokenList);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }
}
