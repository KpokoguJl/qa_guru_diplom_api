package site.kpokogujl.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import site.kpokogujl.domain.Pet;

import java.util.ArrayList;
import java.util.Arrays;

public class PetstoreTests extends TestBase {

    @Test
    public void addPetTest () {
        Pet dog = Pet
                .builder()
                .name("dog")
                .photoUrls(new ArrayList<>(Arrays.asList("https://avatars.mds.yandex.net/i?id=3f630e3cff2e165a39a8536541e8592a-5175116-images-thumbs&n=13")))
                .build();


        Pet responsePet =
                 request()
                        .contentType("application/json")
                        .body(dog)
                        .when()
                        .post("v2/pet")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().body().as(Pet.class);

        assert responsePet.getName().equals("dog");

        System.out.println(responsePet);
    }
}
