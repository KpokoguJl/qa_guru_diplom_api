package site.kpokogujl.helpers.users;

import com.github.javafaker.Faker;
import site.kpokogujl.domain.users.User;

public class PrepareUser {
    static Faker faker = new Faker();

    public static User prepareUser(){
        return User
                .builder()
                .username(faker.name().username())
                .firstName(faker.harryPotter().character())
                .lastName(faker.harryPotter().house())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .build();
    }
}
