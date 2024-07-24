package likelion.MZConnent.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .version("v1.0.0")
                .title("요즘 애들 뭐햐 API")
                .description("요즘 애들 뭐햐 API 목록입니다.");

        return new OpenAPI()
                .info(info);
    }
}