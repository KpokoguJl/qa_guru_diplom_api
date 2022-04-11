package site.kpokogujl.helpers.pets;

import io.qameta.allure.Step;
import site.kpokogujl.domain.pets.Pet;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static site.kpokogujl.specs.pets.PetsSpecs.requestSpec;
import static site.kpokogujl.specs.pets.PetsSpecs.responseSpec;

public class GetPetFromPetstore {
    @Step("Отправляю запрос на получение питомца. Проверяю, что ответ соответствует JSON схеме.")
    public static Pet getPetById(String petId) {

        return given()
                .spec(requestSpec)
                .when()
                .get("pet/" + petId)
                .then()
                .spec(responseSpec)
                .body(matchesJsonSchemaInClasspath("schemas/pets/get_pet_petstore_schema.json"))
                .statusCode(200)
                .extract().as(Pet.class);
    }
}
