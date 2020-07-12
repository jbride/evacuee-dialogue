package com.redhat.cajun.navy.evacuee;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SMSResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/smsconsume")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}