package gram.killergram.domain.email.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EmailVerificationRequest {

    @NotBlank
    @Size(max = 100)
    @jakarta.validation.constraints.Email
    private String email;
}
