package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    private static final String BASE_URI = "https://reqres.in/";
    public static RequestSpecification requestSpecification;

    @BeforeAll
    public static void setUp() {
        requestSpecification = RestAssured.given().baseUri(BASE_URI).contentType(ContentType.JSON).filter(new AllureRestAssured());
    }
}
