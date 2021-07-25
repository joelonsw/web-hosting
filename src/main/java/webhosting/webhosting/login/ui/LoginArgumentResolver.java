package webhosting.webhosting.login.ui;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import webhosting.webhosting.login.domain.LoginPrincipal;
import webhosting.webhosting.member.domain.User;
import webhosting.webhosting.member.domain.UserRepository;
import webhosting.webhosting.login.exception.NotLoggedInException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@AllArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginPrincipal.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        Object socialId = session.getAttribute("socialId");
        if (Objects.isNull(socialId)) {
            throw new NotLoggedInException("로그인 사용자가 없습니다.");
        }
        return userRepository.findBySocialId((String)socialId)
                .orElseThrow(NotLoggedInException::new);
    }
}
