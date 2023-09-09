package the.husky.onlineshoprest.dto;

import lombok.Builder;
import lombok.Data;
import the.husky.onlineshoprest.entity.UserEntity;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDate registrationDate;

    public static User toDto(UserEntity userEntity) {
        long id = userEntity.getUserId();
        String name = userEntity.getName();
        String email = userEntity.getEmail();
        int age = userEntity.getAge();
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .age(age)
                .registrationDate(userEntity.getRegistrationDate())
                .build();
    }
}
