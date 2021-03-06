package site.kpokogujl.tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.kpokogujl.domain.pets.Pet;
import site.kpokogujl.helpers.pets.GetPetFromPetstore;

import java.util.ArrayList;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static site.kpokogujl.helpers.pets.AddPetToPetstore.addPet;
import static site.kpokogujl.helpers.pets.PreparePet.preparePet;
import static site.kpokogujl.specs.pets.PetsSpecs.requestSpec;
import static site.kpokogujl.specs.pets.PetsSpecs.responseSpec;

public class PetsTests {

    @Test
    @Owner("allure8")
    @Feature("API test Petstore.swagger.io")
    @Story("Pets tests")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Добавление нового питомца в магазин")
    public void addPetTest() {
        Pet pet = preparePet();
        Pet newPetInStore = addPet(pet);

        step("Проверяю что в ответе корректные данные. Имя: " + newPetInStore.getName() + " == SomePetName");
        assertThat(newPetInStore.getName()).isEqualTo(pet.getName());
        step("Проверяю что в ответе корректные данные. URL фото.");
        assertThat(newPetInStore.getPhotoUrls()).isEqualTo(pet.getPhotoUrls());
    }

    @Test
    @Owner("allure8")
    @Feature("API test Petstore.swagger.io")
    @Story("Pets tests")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Получение питомца по его идентификатору")
    public void getPetByIdTest() {
        Pet pet = preparePet();
        Pet newPetInStore = addPet(pet);

        Pet petFromPetstore = GetPetFromPetstore.getPetById(String.valueOf(newPetInStore.getId()));

        step("Проверяю что в ответе корректные данные. Имя: " + petFromPetstore.getName() + " == SomePetName");
        assertThat(petFromPetstore.getName()).isEqualTo(pet.getName());
        step("Проверяю что в ответе корректные данные. URL фото." + petFromPetstore.getName() + " == SomePetName");
        assertThat(petFromPetstore.getPhotoUrls()).isEqualTo(pet.getPhotoUrls());
    }

    @Test
    @Owner("allure8")
    @Feature("API test Petstore.swagger.io")
    @Story("Pets tests")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Обновление информации о питомце в магазине")
    public void updatePetTest() {
        Pet pet = preparePet();
        Pet newPetInStore = addPet(pet);

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

        step("Получаю ответ: " + response.statusCode());
        step("Проверяю что ответ соответствует JSON схеме.");

        step("Проверяю что в ответе корректные данные. Имя: " + response.as(Pet.class).getName() + " == SomePetName");
        assertThat(response.as(Pet.class).getName()).isEqualTo("NewPetName");
        step("Проверяю что в ответе корректные данные. URL фото.");
        assertThat(response.as(Pet.class).getPhotoUrls()).contains("none");
    }

    @Test
    @Owner("allure8")
    @Feature("API test Petstore.swagger.io")
    @Story("Pets tests")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Удаление питомца из магазина")
    public void deletePetTest() {
        Pet pet = preparePet();
        Pet newPetInStore = addPet(pet);

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

        step("Получаю ответ: " + response.statusCode());
        step("Проверяю что ответ соответствует JSON схеме.");

        step("Проверяю что в ответе корректные данные.");
        assert response.body().path("code").equals(200);
        assert response.body().path("type").equals("unknown");
        assert response.body().path("message").equals(String.valueOf(newPetInStore.getId()));
    }
}
