package site.kpokogujl.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import site.kpokogujl.domain.pets.Pet;
import site.kpokogujl.helpers.pets.AddPetToPetstore;
import site.kpokogujl.helpers.pets.GetPetFromPetstore;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static site.kpokogujl.helpers.pets.PreparePet.preparePet;
import static site.kpokogujl.specs.pets.PetsSpecs.requestSpec;
import static site.kpokogujl.specs.pets.PetsSpecs.responseSpec;

public class PetsTests {

    @Test
    public void addPetTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = preparePet();
        step("Питомец подготовлен: " + pet);

        step("Отправляю запрос на добавление питомца.");
        Pet newPetInStore = AddPetToPetstore.addPet(pet);

        step("Проверяю что в ответе корректные данные. Имя: " + newPetInStore.getName() + " == SomePetName");
        assertThat(newPetInStore.getName()).isEqualTo(pet.getName());
        step("Проверяю что в ответе корректные данные. URL фото.");
        assertThat(newPetInStore.getPhotoUrls()).isEqualTo(pet.getPhotoUrls());
    }

    @Test
    public void getPetByIdTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = preparePet();
        step("Питомец подготовлен: " + pet);

        step("Отправляю запрос на добавление питомца.");
        Pet newPetInStore = AddPetToPetstore.addPet(pet);

        step("Отправляю запрос на получение питомца.");
        Pet petFromPetstore = GetPetFromPetstore.getPetById(String.valueOf(newPetInStore.getId()));

        step("Проверяю что в ответе корректные данные. Имя: " + petFromPetstore.getName() + " == SomePetName");
        assertThat(petFromPetstore.getName()).isEqualTo(pet.getName());
        step("Проверяю что в ответе корректные данные. URL фото." + petFromPetstore.getName() + " == SomePetName");
        assertThat(petFromPetstore.getPhotoUrls()).isEqualTo(pet.getPhotoUrls());
    }

    @Test
    public void updatePetTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = preparePet();
        step("Питомец подготовлен: " + pet);

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
                        .body(matchesJsonSchemaInClasspath("schemas/pets/post_pet_petstore_schema.json"))
                        .extract().response();

        step("Получен ответ: " + response.statusCode());
        step("Ответ соответствует JSON схеме.");

        step("Проверяю что в ответе корректные данные. Имя: " + response.as(Pet.class).getName() + " == SomePetName");
        assertThat(response.as(Pet.class).getName()).isEqualTo("NewPetName");
        step("Проверяю что в ответе корректные данные. URL фото.");
        assertThat(response.as(Pet.class).getPhotoUrls()).contains("none");
    }

    @Test
    public void deletePetTest () {

        step("Подготавливаю нового питомца.");
        Pet pet = preparePet();
        step("Питомец подготовлен: " + pet);

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
                        .body(matchesJsonSchemaInClasspath("schemas/pets/delete_pet_petstore_schema.json"))
                        .extract().response();

        step("Получен ответ: " + response.statusCode());
        step("Ответ соответствует JSON схеме.");

        step("Проверяю что в ответе корректные данные.");
        assert response.body().path("code").equals(200);
        assert response.body().path("type").equals("unknown");
        assert response.body().path("message").equals(String.valueOf(newPetInStore.getId()));
    }
}
