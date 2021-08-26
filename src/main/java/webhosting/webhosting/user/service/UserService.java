package webhosting.webhosting.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webhosting.webhosting.hosting.domain.HostingFile;
import webhosting.webhosting.user.domain.User;
import webhosting.webhosting.user.domain.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private UserRepository userRepository;

    public String saveAndGetSocialId(User user) {
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


    @Transactional(readOnly = true)
    public List<String> findAllUserHTMLFileName() {
        final List<User> users = userRepository.findAllWithFetchJoin();
        log.info(">>>>>>[유저의 리소스를 추출해봅시다]<<<<<<<");
        log.info("User Size : {}", users.size());

        return users.stream()
                .map(user -> user.findHostingHTML().getFilePath())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> findResourcePaths(String userName) {
        final User user = userRepository.findByName(userName).orElseThrow(IllegalArgumentException::new);
        final List<HostingFile> hostingFiles = user.getHostingFiles();
        return hostingFiles.stream()
                .map(HostingFile::getFilePath)
                .collect(Collectors.toList());
    }
}
