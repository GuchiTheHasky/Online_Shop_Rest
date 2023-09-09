package the.husky.onlineshoprest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "login", unique = true)
    @NotBlank(message = "Login is mandatory")
    private String login;

    @Column(name = "password")
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "email", unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "age")
    @Min(value = 1, message = "Age should be greater than 0")
    @Max(value = 150, message = "Age should be less than 150")
    private int age;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @PrePersist
    private void injectDate() {
        this.registrationDate = LocalDate.now();
    }
}
