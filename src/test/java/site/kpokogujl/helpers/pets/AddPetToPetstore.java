package site.kpokogujl.helpers.pets;

import io.qameta.allure.Step;
import site.kpokogujl.domain.pets.Pet;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static site.kpokogujl.specs.pets.PetsSpecs.requestSpec;
import static site.kpokogujl.specs.pets.PetsSpecs.responseSpec;

public class AddPetToPetstore {
    @Step("Отправляю запрос на добавление питомца. Проверяю что ответ соответстует схеме.")
    public static Pet addPet(Pet pet) {

        return given()
                .spec(requestSpec)
                .body(pet)
                .when()
                .post("pet")
                .then()
                .spec(responseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/pets/post_pet_petstore_schema.json"))
                .statusCode(200)
                .extract().as(Pet.class);
    }
}
