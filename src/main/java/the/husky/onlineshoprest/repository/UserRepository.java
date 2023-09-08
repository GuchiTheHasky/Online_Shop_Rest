package the.husky.onlineshoprest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import the.husky.onlineshoprest.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByEmail(String email);
    Optional<List<UserEntity>> findAllByName(String name);
    Optional<List<UserEntity>> findAllByAge(int age);
}

