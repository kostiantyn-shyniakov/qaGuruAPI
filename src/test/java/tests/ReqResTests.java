package tests;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import model.RegisterUserModel;
import model.UserModel;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqResTests extends BaseTest {

    final Faker faker = new Faker();
    final Gson gson = new Gson();



    @Test
    public void createUser(){
        String name = faker.address().firstName();
        String job = faker.job().title();
        UserModel userModel = new UserModel(name, job);
        String json = gson.toJson(userModel);
        given().spec(requestSpecification)
                .when().body(json)
                .post("/api/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().body()
                .body("id",is(notNullValue()))
                .body("name", equalTo(name))
                .body("job", equalTo(job));
    }

    @Test
    public void registerUserSuccessful() {
        String emailAddress = faker.internet().emailAddress();
        String password = "password" + faker.number().digits(4);
        RegisterUserModel registerUserModel = new RegisterUserModel(emailAddress, password);
        String json = gson.toJson(registerUserModel);
        given().spec(requestSpecification)
                .when().body(json)
                .post("api/api/register")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .log().body()
                .body("id",is(notNullValue()));
    }

    @Test
    public void registerUserUnsuccessful() {

        given().spec(requestSpecification)
                .when().body("{\"email\":\"robin.jenkins@gmail.com\"}")
                .post("/api/register")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .log().body()
                .body("error",equalTo("Missing password"));
    }

    @Test
    public void updateUser() {
        String name = faker.name().name();
        String job = faker.job().title();
        UserModel userModel = new UserModel(name, job);
        String json = gson.toJson(userModel);
        given().spec(requestSpecification)
                .when().body(json)
                .put("/api/users/1")
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .log().body()
                .body("name", equalTo(name))
                .body("job", equalTo(job));
    }

    @Test
    public void getUserById() {
        given().spec(requestSpecification)
                .when()
                .get("/api/users/1")
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .log().body()
                .body("data.id",
                        equalTo(1));
    }
}
