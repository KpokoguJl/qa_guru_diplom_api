package site.kpokogujl.helpers.users;

import io.restassured.response.Response;
import site.kpokogujl.domain.users.User;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static site.kpokogujl.specs.users.UsersSpecs.userRequestSpec;
import static site.kpokogujl.specs.users.UsersSpecs.userResponseSpec;

public class CreateUserInPetstore {

    public static User createUser(User user) {

        step("Отправляю запрос на создание юзера.");

        Response response =
                given()
                        .spec(userRequestSpec)
                        .body(user)
                        .when()
                        .post("user")
                        .then()
                        .spec(userResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/users/post_user_petstore_schema.json"))
                        .statusCode(200)
                        .extract().response();

        step("Получен ответ: " + response.statusCode());
        step("Ответ соответствует JSON схеме.");

        user.setId(Long.parseLong(response.body().path("message")));

        return user;
    }
}
