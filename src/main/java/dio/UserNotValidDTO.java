package dio;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserNotValidDTO {

        private int id;
        private String usernameNotValid;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phone;
        private int userStatus;


}
