package ru.nxckywhxte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nxckywhxte.entity.admin.AdminEntity;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<AdminEntity, UUID> {

    Optional<AdminEntity> findByEmail(String email);
    AdminEntity save(AdminEntity admin);
}
