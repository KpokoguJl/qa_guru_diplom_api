package site.kpokogujl.helpers;

import site.kpokogujl.tests.TestBase;

public class Auth extends TestBase {
    private final String url = "login";

    public String getAuthCookie (){

        return request()
                .contentType("application/x-www-form-urlencoded")
                .formParam("Email", USER_LOGIN)
                .formParam("Password", USER_PASSWORD)
                .formParam("RememberMe", "false")
                .when()
                .post(url)
                .then()
//                .log().all()
                .statusCode(302)
                .extract().cookie("NOPCOMMERCE.AUTH");
    }
}
