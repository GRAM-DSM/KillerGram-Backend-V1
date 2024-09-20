package gram.killergram.domain.user.controller.dto.request;

import gram.killergram.domain.user.domain.type.Ability;
import gram.killergram.domain.user.domain.type.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSignUpRequest {

    @NotBlank
    @Size(max = 30)
    private String accountId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$")
    private String password;

    @NotBlank
    private String deviceToken;

    @NotBlank
    private String name;

    @NotBlank
    private String schoolNumber;

    @NotNull
    private Gender gender;

    @NotNull
    private Ability ability;
}