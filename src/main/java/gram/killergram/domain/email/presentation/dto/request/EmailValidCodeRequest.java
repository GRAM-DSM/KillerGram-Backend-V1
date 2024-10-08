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
public class EmailValidCodeRequest {

    @NotBlank
    private String code;

    @NotBlank
    @Size(max = 30)
    private String email;
}