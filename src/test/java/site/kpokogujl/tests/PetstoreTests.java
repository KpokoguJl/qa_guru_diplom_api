package site.kpokogujl.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import site.kpokogujl.domain.Pet;
import site.kpokogujl.helpers.AddPetToPetstore;
import site.kpokogujl.helpers.GetPetFromPetstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static site.kpokogujl.specs.PetstoreSpecs.requestSpec;
import static site.kpokogujl.specs.PetstoreSpecs.responseSpec;

public class PetstoreTests {

    private final String photoUrl = "https://avatars.mds.yandex.net/i?id=3f630e3cff2e165a39a8536541e8592a-5175116-images-thumbs&n=13";

    @Test
    public void addPetTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = Pet
                .builder()
                .name("SomePetName")
                .photoUrls(new ArrayList<>(List.of(photoUrl)))
                .build();

        step("Питомец создан: " + pet);

        step("Отправляю запрос на добавление питомца.");
        Pet newPetInStore = AddPetToPetstore.addPet(pet);

        step("Проверяю что в ответе корректные данные. Имя: " + newPetInStore.getName() + " == SomePetName");
        assert newPetInStore.getName().equals("SomePetName");
        step("Проверяю что в ответе корректные данные. URL фото.");
        assert newPetInStore.getPhotoUrls().contains(photoUrl);
    }

    @Test
    public void getPetByIdTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = Pet
                .builder()
                .name("SomePetName")
                .photoUrls(new ArrayList<>(List.of(photoUrl)))
                .build();

        step("Питомец создан: " + pet);

        step("Отправляю запрос на добавление питомца.");
        Pet newPetInStore = AddPetToPetstore.addPet(pet);

        step("Отправляю запрос на получение питомца.");
        Pet petFromPetstore = GetPetFromPetstore.getPetById(String.valueOf(newPetInStore.getId()));

        step("Проверяю что в ответе корректные данные. Имя: " + petFromPetstore.getName() + " == SomePetName");
        assert petFromPetstore.getName().equals("SomePetName");
        step("Проверяю что в ответе корректные данные. URL фото." + petFromPetstore.getName() + " == SomePetName");
        assert petFromPetstore.getPhotoUrls().contains(photoUrl);
    }

    @Test
    public void updatePetTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = Pet
                .builder()
                .name("SomePetName")
                .photoUrls(new ArrayList<>(List.of(photoUrl)))
                .build();

        step("Питомец создан: " + pet);

        step("Отправляю запрос на добавление питомца.");
        Pet newPetInStore = AddPetToPetstore.addPet(pet);

        step("Меняю данные питомца.");
        newPetInStore.setName("NewPetName");
        newPetInStore.setPhotoUrls(new ArrayList<>(List.of("none")));

        step("Отправляю запрос на обновление данных питомца.");
        Response response =
                given()
                        .spec(requestSpec)
                        .body(newPetInStore)
                    .when()
                        .put("pet")
                    .then()
                        .spec(responseSpec)
                        .log().all()
                        .body(matchesJsonSchemaInClasspath("schemas/post_pet_petstore_schema.json"))
                        .extract().response();

        step("Получен ответ: " + response.statusCode());
        step("Ответ соответствует JSON схеме.");

        step("Проверяю что в ответе корректные данные. Имя: " + response.as(Pet.class).getName() + " == SomePetName");
        assert response.as(Pet.class).getName().equals("NewPetName");
        step("Проверяю что в ответе корректные данные. URL фото.");
        assert response.as(Pet.class).getPhotoUrls().contains("none");
    }

    @Test
    public void deletePetTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = Pet
                .builder()
                .name("SomePetName")
                .photoUrls(new ArrayList<>(List.of(photoUrl)))
                .build();

        step("Питомец создан: " + pet);

        step("Отправляю запрос на добавление питомца.");
        Pet newPetInStore = AddPetToPetstore.addPet(pet);

        step("Отправляю запрос на удаление питомца.");
        Response response =
                given()
                        .spec(requestSpec)
                        .body(newPetInStore)
                    .when()
                        .delete("pet/" + newPetInStore.getId())
                    .then()
                        .spec(responseSpec)
                        .log().all()
                        .body(matchesJsonSchemaInClasspath("schemas/delete_pet_petstore_schema.json"))
                        .extract().response();

        step("Получен ответ: " + response.statusCode());
        step("Ответ соответствует JSON схеме.");

        step("Проверяю что в ответе корректные данные.");
        assert response.body().path("code").equals(200);
        assert response.body().path("type").equals("unknown");
        assert response.body().path("message").equals(String.valueOf(newPetInStore.getId()));
    }
}
