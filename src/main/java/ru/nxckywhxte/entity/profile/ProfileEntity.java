package ru.nxckywhxte.entity.profile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nxckywhxte.entity.user.UserEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Table(name = "profiles")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToOne(mappedBy = "profile")
    private UserEntity user;
}
