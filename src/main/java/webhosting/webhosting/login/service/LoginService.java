package webhosting.webhosting.login.service;

import org.springframework.stereotype.Service;
import webhosting.webhosting.member.domain.User;
import webhosting.webhosting.member.domain.UserRepository;

import java.util.Optional;

@Service
public class LoginService {
    private GoogleLoginService googleLoginService;
    private UserRepository userRepository;

    public LoginService(GoogleLoginService googleLoginService, UserRepository userRepository) {
        this.googleLoginService = googleLoginService;
        this.userRepository = userRepository;
    }

    public String generateGoogleRedirectUrl() {
        return googleLoginService.generateRedirectUrl();
    }

    public String oauthGoogle(String code) {
        final String accessToken = googleLoginService.generateAccessToken(code);
        final User user = googleLoginService.generateUser(accessToken);
        if (checkAlreadyMember(user)) {
            return user.getSocialId();
        }
        final User savedUser = saveUser(user);
        return savedUser.getName();
    }

    private boolean checkAlreadyMember(User user) {
        final Optional<User> signedUpUser = userRepository.findBySocialId(user.getSocialId());
        return signedUpUser.isPresent();
    }

    public User saveUser(User user) {
        String socialLoginName = user.getName();
        Optional<User> duplicateNameUser = userRepository.findByName(socialLoginName);
        int number = 2;
        while (duplicateNameUser.isPresent()) {
            String duplicateMark = "(" + number + ")";
            duplicateNameUser = userRepository.findByName(socialLoginName + duplicateMark);
            number++;
            user.setName(socialLoginName + duplicateMark);
        }
        return userRepository.save(user);
    }
}
