package site.kpokogujl.helpers.pets;

import io.restassured.response.Response;
import site.kpokogujl.domain.pets.Pet;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static site.kpokogujl.specs.pets.PetsSpecs.requestSpec;
import static site.kpokogujl.specs.pets.PetsSpecs.responseSpec;

public class AddPetToPetstore {
    public static Pet addPet (Pet pet) {

        step("Отправляю запрос на добавление питомца.");
        Response response =
                given()
                        .spec(requestSpec)
                        .body(pet)
                    .when()
                        .post("pet")
                    .then()
                        .spec(responseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/pets/post_pet_petstore_schema.json"))
                        .statusCode(200)
                        .extract().response();

        step("Получен ответ: " + response.statusCode());
        step("Ответ соответствует JSON схеме.");

        return  response.as(Pet.class);
    }
}
