
import dio.UserDTO;
import dio.UserNotValidDTO;
import dio.UserResponseDTO;
import ex.InpossibleCreatUserException;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.UserApi;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class CreateUser_Test {
    private UserApi petStoreApi = new UserApi();
    private UserNotValidDTO userNotValid = new UserNotValidDTO();
    //Успешное создание пользователя
    @Test
    public void checkCrestUser() throws InpossibleCreatUserException {
        String userName = "Name123";
        while (petStoreApi.getUserForName(userName).statusCode(200).equals(true)){
                petStoreApi.deleteUser(userName);
        }

        UserDTO user1 = UserDTO.builder()
                .phone("simwNUmber")
                .userStatus(900)
                .firstName("firstName")
                .username(userName)
                .build();
//        if (petStoreApi.getUserForName(userName).extract().body().jsonPath().get("message").equals("User not found")) {
            petStoreApi.createUser(user1)
                    .statusCode(200)
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userCreate.json"))
                    .time(lessThan(5000L));
            UserResponseDTO response = petStoreApi.createUser(user1)
                    .extract().body().as(UserResponseDTO.class);
            Assertions.assertAll("checkUserDTO",
                    () -> Assertions.assertEquals(200, response.getCode(), "Incorrect code"),
                    () -> Assertions.assertEquals("unknown", response.getType(), "Incorrect type")
            );
            petStoreApi.getUserForName(userName)
                    .statusCode(200);

 //       }else {new InpossibleCreatUserException("user already exists");}
    }

    @Test
    public void chechNotCreateUser() {
//передача невалидногго json не соответсвуующего твобования для создания объекта. проверка что user не создается
        String userName = "Name123noValid";
        UserNotValidDTO userNotValid = UserNotValidDTO.builder()
                .usernameNotValid(userName)
                .phone("kgj")
                .userStatus(200)
                .firstName(userName)
                .build();
        petStoreApi.createUserNotValid(userNotValid)
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userCreate.json"))
                .time(lessThan(5000L));
        UserResponseDTO response = petStoreApi.createUserNotValid(userNotValid)
                .extract().body().as(UserResponseDTO.class);
        Assertions.assertAll("checkUserDTO",
                () -> Assertions.assertEquals(200, response.getCode(), "Incorrect code"),
                () -> Assertions.assertEquals("unknown", response.getType(), "Incorrect type")
        );
        petStoreApi.getUserForName(userName)
                .statusCode(404)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("User not found"));
    }

}

