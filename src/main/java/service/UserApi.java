package service;
import dio.AunthenUserDTO;
import dio.UserDTO;
import dio.UserNotValidDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserApi {

    private static final String BASE_USL = "https://petstore.swagger.io/v2/";
    private static final String USER_PATH = "/user";
    private RequestSpecification spec;

    public UserApi() {
        spec = given()
                .baseUri(BASE_USL)
                .contentType(ContentType.JSON)
                .log().all();

    }

    public ValidatableResponse createUser(UserDTO userDTO) {
        return given(spec)
                    .basePath(USER_PATH)
                    .body(userDTO)
                .when()
                    .post()
                .then()
                    .log().all();
    }

    public ValidatableResponse getUserForName(String name) {
        String nameTest = String.format(USER_PATH + "/" + name);
        return given(spec)
                    .basePath(nameTest)
                .when()
                    .get()
                .then()
                    .log().all();

    }
    public ValidatableResponse createUserNotValid(UserNotValidDTO userNotValidDTO) {
        return given(spec)
                    .basePath(USER_PATH)
                    .body(userNotValidDTO)
                .when()
                    .post()
                .then()
                    .log().all();
    }

    public ValidatableResponse updateUserWithoutAuthorization(String nameWithoutAuth, UserDTO userDTOWithoutAuth) {
        return given(spec)
                    .basePath(USER_PATH + "/" + nameWithoutAuth)
                    .body(userDTOWithoutAuth)
                .when()
                    .put()
                .then()
                    .log().all();

    }
    public ValidatableResponse updateUser(String name, UserDTO userDTO,String session) {
        return given(spec)
//                .header("Authorization",session)
                .basePath(USER_PATH + "/" + name)
                .body(userDTO)
                .when()
                .put()
                .then()
                .log().all();

    }
    public ValidatableResponse deleteUser(String name) {
        return given(spec)
                    .basePath(USER_PATH + "/" + name)
                .when()
                    .delete()
                .then()
                    .log().all();
    }

    public ValidatableResponse authentication(AunthenUserDTO userData) {
        return given(spec)
                    .basePath(USER_PATH + "/login")
                    .body(userData)
                .when()
                    .get()
                .then()
                    .log().all();
    }
}