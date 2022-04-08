package site.kpokogujl.helpers;

import io.restassured.response.Response;
import site.kpokogujl.domain.Pet;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static site.kpokogujl.specs.PetstoreSpecs.requestSpec;
import static site.kpokogujl.specs.PetstoreSpecs.responseSpec;

public class GetPetFromPetstore {
    public static Pet getPetById (String petId) {

        step("Отправляю запрос на получение питомца.");
        Response response =
                given()
                        .spec(requestSpec)
                    .when()
                        .get("pet/" + petId)
                    .then()
                        .spec(responseSpec)
                        .log().all()
                        .body(matchesJsonSchemaInClasspath("schemas/get_pet_petstore_schema.json"))
                        .extract().response();

        step("Получен ответ: " + response.statusCode());
        step("Ответ соответствует JSON схеме.");

        return response.as(Pet.class);
    }
}
