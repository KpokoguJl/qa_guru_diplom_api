package site.kpokogujl.tests;

import io.restassured.specification.RequestSpecification;
import site.kpokogujl.config.ConfigHelper;
import site.kpokogujl.helpers.CustomAllureListener;

import static io.restassured.RestAssured.given;

public class TestBase {

    protected static final String API_BASE_URL = ConfigHelper.getBaseURL();
    public  static final String USER_LOGIN = ConfigHelper.apiLogin();
    protected static String USER_PASSWORD = ConfigHelper.apiPassword();
    public static String USER_NAME = ConfigHelper.apiUserName();

    final RequestSpecification request = given()
            .filters(CustomAllureListener.withCustomTemplates())
            .baseUri(API_BASE_URL);

    public RequestSpecification request() {
        return request;
    }
}
