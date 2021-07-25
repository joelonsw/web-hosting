package webhosting.webhosting.login.ui;

import org.springframework.web.servlet.HandlerInterceptor;
import webhosting.webhosting.user.domain.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class LoginInterceptor implements HandlerInterceptor {
    private UserRepository userRepository;

    public LoginInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        Object socialId = session.getAttribute("socialId");
        if (Objects.isNull(socialId) || !userRepository.findBySocialId((String) socialId).isPresent()) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
