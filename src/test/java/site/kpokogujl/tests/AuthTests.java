package site.kpokogujl.tests;

import org.junit.jupiter.api.Test;
import site.kpokogujl.tests.TestBase;

public class AuthTests extends TestBase {
    @Test
    public void authTest () {
        String url = "login";

        request()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", USER_LOGIN)
                .formParam("Password", USER_PASSWORD)
                .formParam("RememberMe", "false")
                .when()
                .post(url)
                .then()
//                .log().all()
                .statusCode(302);
    }
}
