
import dio.UserDTO;
import dio.UserResponseDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.UserApi;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CrestNewUser_Test {
    private UserApi petStoreApi = new UserApi();

    @Test
    public void checkCrestUser() {
        String userName = "Name123";
        UserDTO user1 = UserDTO.builder()
                .phone("simwNUmber")
                .userStatus(900)
                .firstName("firstName")
                .username(userName)
                .build();
//        UserDTO user0 = UserDTO.builder()
//                .phone("86986869")
//                .userStatus(900)
//                .password("riiie")
//                .email("jejej@jje.ru")
//                .build();
        //v1
        //hamMatcher
        petStoreApi.createUser(user1)
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userCreate.json"))
                .time(lessThan(5000L));
        UserResponseDTO response = petStoreApi.createUser(user1)
                                                .extract().body().as(UserResponseDTO.class);
        Assertions.assertAll("checkUserDTO",
                () -> Assertions.assertEquals(200, response.getCode(), "Incorrect code"),
                () -> Assertions.assertEquals("unknown", response.getType(), "Incorrect type")
//                () -> Assertions.assertEquals("0", response.getMessage(), "Incorrect message")
        );

        petStoreApi.getUserForName(userName)
                .statusCode(200);

//        UserResponseDTO  getResponse = petStoreApi.getUserForName(userName)
//                                            .extract().body().as(UserResponseDTO.class);
//

    }
//
//                .body("code", equalTo(200))
//                .body("type",equalTo("unknown"))
//                .body("message", equalTo("0"));
        //v2
//        UserResponseDTO response = petStoreApi.createUser(user1)
//                .extract().body().as(UserResponseDTO.class);
//        Assertions.assertAll("checkUserDTO",
//            () -> Assertions.assertEquals(200, response.getCode(), "Incorrect code"),
//            () -> Assertions.assertEquals("unknown", response.getType(), "Incorrect type"),
//            () -> Assertions.assertEquals("0", response.getMessage(), "Incorrect message")
//        );
        //v3
//        String actualMessage = petStoreApi.createUser(user0)
//                .extract().body().jsonPath().get("message");
//
//        Assertions.assertEquals("0",actualMessage);

        //Проветка лишних полей ответа (jsonSchemaValid)

//        petStoreApi.createUser(user1)
//                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userCreate.json"))
//                .time(lessThan(5000L));

//    }

}
