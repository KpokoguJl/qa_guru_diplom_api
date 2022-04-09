package site.kpokogujl.tests;

import org.junit.jupiter.api.Test;
import site.kpokogujl.domain.users.User;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static site.kpokogujl.helpers.users.CreateUserInPetstore.createUser;
import static site.kpokogujl.helpers.users.PrepareUser.prepareUser;
import static site.kpokogujl.specs.users.UsersSpecs.userRequestSpec;
import static site.kpokogujl.specs.users.UsersSpecs.userResponseSpec;

public class UsersTests {

    @Test
    public void createUserTest() {

        step("Подготавливаю нового юзера.");
        User user = prepareUser();
        step("Юзер подготовлен: " + user);

        step("Отправляю запрос на создание юзера.");
        user.setId(createUser(user).getId());

        step("Проверяю, что id юзера заполнен.");
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void getUserByUsernameTest(){

        step("Подготавливаю нового юзера.");
        User user = prepareUser();
        step("Юзер подготовлен: " + user);

        step("Отправляю запрос на создание юзера.");
        user.setId(createUser(user).getId());

        step("Отправляю запрос на получение юзера по username.");
        User userFromApi =
                given()
                        .spec(userRequestSpec)
                    .when()
                        .get("user/" + user.getUsername())
                    .then()
                        .spec(userResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemas/users/get_user_by_username_petstore_schema.json"))
                        .extract().response().as(User.class);

        step("Ответ получен.");

        step("Проверяю, что вернулся юзер, который запрашивался.");
        assertThat(userFromApi).isEqualTo(user);
    }
}