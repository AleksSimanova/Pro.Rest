import static org.hamcrest.Matchers.*;

import dio.AunthenUserDTO;
import dio.ResponseGetUserDTO;
import dio.UserDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.UserApi;
import supportive.SplitLine;
import supportive.TextRandom;

public class AdditionUser_Test {
    private UserApi petStoreApi = new UserApi();
    private AunthenUserDTO auntApi = new AunthenUserDTO();

    @Test
    public void additionUser() {
        String name = TextRandom.generateString(5);

        UserDTO userLast = UserDTO.builder()
                .userStatus(900)
                .firstName("firstName")
                .username(name)
                .build();

        AunthenUserDTO autorizUser = AunthenUserDTO.builder()
                .username(name)
                .password(name)
                .build();

        String phoneRandom = TextRandom.generateString(5);
        UserDTO additionUserDTO = UserDTO.builder()
                .phone(phoneRandom)
                .build();

        petStoreApi.createUser(userLast)
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userCreate.json"))
                .time(lessThan(5000L));
        petStoreApi.getUserForName(name)
                .statusCode(200)
                .body("username", equalTo(name));
        petStoreApi.authentication(autorizUser)
                .statusCode(200);
        String sessionMessage = petStoreApi.authentication(autorizUser)
                .extract()
                .body()
                .jsonPath()
                .get("message");

        String session = SplitLine.getSession(sessionMessage);

        petStoreApi.updateUser(name, additionUserDTO, session)
                .statusCode(200)
                .body("type", equalTo("unknown"))
                .body("message", notNullValue());
        petStoreApi.getUserForName(name)
                .statusCode(200);

        ResponseGetUserDTO response = petStoreApi.getUserForName(name)
                .extract()
                .body()
                .as(ResponseGetUserDTO.class);

        Assertions.assertAll("check body after addition",
                () -> Assertions.assertNotEquals(phoneRandom, response.getPhone(), "check phone"),
                () -> Assertions.assertEquals(name, response.getUsername(), "name not valid"),
                () -> Assertions.assertEquals("firstName", response.getFirstName(), "not valid firstName"));
    }
}
