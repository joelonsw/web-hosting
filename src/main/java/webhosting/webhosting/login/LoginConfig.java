package webhosting.webhosting.login;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import webhosting.webhosting.login.ui.LoginInterceptor;
import webhosting.webhosting.user.domain.UserRepository;
import webhosting.webhosting.login.ui.LoginArgumentResolver;

import java.util.List;

@Configuration
@AllArgsConstructor
public class LoginConfig implements WebMvcConfigurer {
    private UserRepository userRepository;

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(createLoginArgumentResolver());
    }

    @Bean
    public LoginArgumentResolver createLoginArgumentResolver() {
        return new LoginArgumentResolver(userRepository);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(userRepository))
                .addPathPatterns("/deploy")
                .addPathPatterns("/mypage");
    }
}
