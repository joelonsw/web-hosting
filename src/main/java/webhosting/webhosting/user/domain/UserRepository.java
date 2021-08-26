package webhosting.webhosting.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    void save(List<User> users);
    Optional<User> findByName(String name);
    Optional<User> findBySocialId(String socialId);

    @Query("select distinct u from User u join fetch u.hostingFiles")
    List<User> findAllWithFetchJoin();
}
