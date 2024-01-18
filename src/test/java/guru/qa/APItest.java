package guru.qa;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import jdk.jfr.Description;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.json.Json;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.*;

@Description("Тестирование API ресурса \"https://reqres.in\"")
public class APItest {
    private final static String URL = "https://reqres.in";
    private final static String AUTH_DATA = "";


    @Test
    @Tag("API")
    @Description("Получение списка пользователей")
    protected void gettingAListOfUsersTest() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(12));
    }
    @Test
    @Tag("API")
    @Description("Создание пользователя")
    protected void creatingAUserTest() {
        given()
                .body("{\"name\": \"morpheus\", \"job\": \"leader\"}")
                .when()
                .contentType(ContentType.JSON)
                .post(URL + "/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .assertThat().body("name", is("morpheus")).and().body("job", is("leader"));
    }

    @Test
    @Tag("API")
    @Description("Регистрация пользователя")
    protected void userRegistration() {
        given()
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}")
                .when()
                .contentType(ContentType.JSON)
                .post(URL + "/api/register")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    @Tag("API")
    @Description("Удаление пользователя")
    protected void userDeleteTest() {
            given()
                    .when()
                    .delete(URL + "/api/users/2")
                    .then()
                    .statusCode(204)
                    .body(emptyString());
    }

    @Test
    @Tag("API")
    @Description("Получение запоздалого ответа")
    protected void gettingADelatedResponse() {
        given()
                .when()
                .get(URL + "/api/users?delay=3")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}
