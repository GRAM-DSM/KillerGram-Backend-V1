package gram.killergram.domain.email.presentation.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifiedEmailResponse {
    @NotBlank
    @Size(max = 30)
    private String email;
}
