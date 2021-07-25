package webhosting.webhosting.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.user.domain.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public String register(User user) {
        if (checkAlreadyMember(user)) {
            return user.getSocialId();
        }
        final User savedUser = saveNewMember(user);
        return savedUser.getSocialId();
    }

    private boolean checkAlreadyMember(User user) {
        final Optional<User> signedUpUser = userRepository.findBySocialId(user.getSocialId());
        return signedUpUser.isPresent();
    }

    private User saveNewMember(User user) {
        String socialLoginName = user.getName();
        Optional<User> duplicateNameUser = userRepository.findByName(socialLoginName);
        int number = 2;
        while (duplicateNameUser.isPresent()) {
            String duplicateMark = "(" + number++ + ")";
            duplicateNameUser = userRepository.findByName(socialLoginName + duplicateMark);
            user.setName(socialLoginName + duplicateMark);
        }
        return userRepository.save(user);
    }
}
