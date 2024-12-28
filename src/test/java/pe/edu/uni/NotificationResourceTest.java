
package pe.edu.uni;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class NotificationResourceTest {

    @Test
    public void testSendNotificationEndpoint() {
        RestAssured.given()
                .contentType("application/json")
                .body("Hello, Kafka!")
                .when()
                .post("/notifications")
                .then()
                .statusCode(202);
    }
}
