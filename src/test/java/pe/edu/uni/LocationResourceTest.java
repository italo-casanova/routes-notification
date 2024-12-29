package pe.edu.uni;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class LocationResourceTest {

    @Test
    public void testLocationUpdateEndpoint() {
        RestAssured.given()
                .contentType("application/json") // Ensure correct content type
                .body("{ \"userId\": \"user123\", \"latitude\": -12.046374, \"longitude\": -77.042793 }")
                .when()
                .post("/locations/update")
                .then()
                .statusCode(200)
                .body(is("Location processed successfully."));
    }
}
