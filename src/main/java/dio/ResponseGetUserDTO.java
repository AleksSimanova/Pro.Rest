package dio;
import lombok.Data;

@Data
public class ResponseGetUserDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;
}
