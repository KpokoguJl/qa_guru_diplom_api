package site.kpokogujl.tests;

import org.junit.jupiter.api.Test;
import site.kpokogujl.tests.TestBase;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GetBooksTests extends TestBase {
    @Test
    void getBooksTestWithSchemaValidation(){

        request()
//                .filter(withCustomTemplates())
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/get_books_schema.json"));
    }
}
