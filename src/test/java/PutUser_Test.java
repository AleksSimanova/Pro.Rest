import static org.hamcrest.Matchers.*;

import dio.AunthenUserDTO;
import dio.UserDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;
import service.UserApi;
import supportive.SplitLine;
import supportive.TextRandom;

public class PutUser_Test {

    private UserApi petStoreApi = new UserApi();
    private AunthenUserDTO auntApi = new AunthenUserDTO();

    @Test
    //Провека работоспособности замены именни user c полным количеством изменяемых данных
    public void updateUser() {
        String nameOld = "nameOLd";
        UserDTO userOLd = UserDTO.builder()
                .phone("number")
                .userStatus(900)
                .firstName("firstName")
                .username(nameOld)
                .build();
        AunthenUserDTO autorizUser = AunthenUserDTO.builder()
                .username(nameOld)
                .password(nameOld)
                .build();

        String newName = TextRandom.generateString(5);
        UserDTO newUserDTO = UserDTO.builder()
                .phone("num")
                .userStatus(900)
                .firstName("newName")
                .username(newName)
                .build();

        petStoreApi.createUser(userOLd)
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/userCreate.json"))
                .time(lessThan(5000L));

        petStoreApi.getUserForName(nameOld)
                        .statusCode(200)
                .body("username", equalTo(nameOld));

        petStoreApi.authentication(autorizUser)
                .statusCode(200);

        String sessionMessage = petStoreApi.authentication(autorizUser)
                .extract().body().jsonPath().get("message");
        String session = SplitLine.getSession(sessionMessage);

        petStoreApi.updateUser(nameOld, newUserDTO, session)
                .statusCode(200)
                .body("type", equalTo("unknown"))
                .body("message", notNullValue());
        petStoreApi.getUserForName(newName).statusCode(200);

    }
}
