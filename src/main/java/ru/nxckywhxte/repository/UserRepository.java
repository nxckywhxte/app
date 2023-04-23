package ru.nxckywhxte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nxckywhxte.entity.user.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID id);
}
