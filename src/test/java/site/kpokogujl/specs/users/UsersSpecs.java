package site.kpokogujl.specs.users;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import site.kpokogujl.config.ConfigHelper;
import site.kpokogujl.helpers.CustomAllureListener;

import static io.restassured.RestAssured.with;

public class UsersSpecs {
    protected static final String API_BASE_URL = ConfigHelper.getBaseURL();


    public static RequestSpecification userRequestSpec =
            with()
                    .filters(CustomAllureListener.withCustomTemplates())
                    .baseUri(API_BASE_URL)
                    .basePath("v2/")
                    .contentType(ContentType.JSON)
                    .log().all();

    public static ResponseSpecification userResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();
}
