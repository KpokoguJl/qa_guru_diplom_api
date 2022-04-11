package site.kpokogujl.helpers.pets;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import site.kpokogujl.domain.pets.Pet;

import java.util.ArrayList;
import java.util.List;

public class PreparePet {
    static Faker faker = new Faker();

    @Step("Подготавливаю нового питомца.")
    public static Pet preparePet() {
        return Pet
                .builder()
                .name(faker.superhero().name())
                .photoUrls(new ArrayList<>(List.of(faker.internet().url())))
                .build();
    }
}
