package site.kpokogujl.helpers.pets;

import com.github.javafaker.Faker;
import site.kpokogujl.domain.pets.Pet;

import java.util.ArrayList;
import java.util.List;

public class PreparePet {
    static Faker faker = new Faker();

    public static Pet preparePet(){
        return  Pet
                .builder()
                .name(faker.superhero().name())
                .photoUrls(new ArrayList<>(List.of(faker.internet().url())))
                .build();
    }
}
