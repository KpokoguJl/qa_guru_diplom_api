package site.kpokogujl.helpers.users;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import site.kpokogujl.domain.users.User;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static site.kpokogujl.specs.users.UsersSpecs.userRequestSpec;
import static site.kpokogujl.specs.users.UsersSpecs.userResponseSpec;

public class CreateUserInPetstore {

    @Step("Отправляю запрос на создание юзера. Проверяю, что ответ соответствует JSON схеме.")
    public static User createUser(User user) {

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
        user.setId(Long.parseLong(response.body().path("message")));

        return user;
    }
}
