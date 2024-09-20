package gram.killergram.domain.user.presentation;

import gram.killergram.domain.user.presentation.dto.request.StudentSignUpRequest;
import gram.killergram.domain.user.presentation.dto.request.UserLoginRequest;
import gram.killergram.domain.user.domain.service.StudentSignUpService;
import gram.killergram.domain.user.domain.service.UserLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final StudentSignUpService studentSignUpService;
    private final UserLoginService userLoginService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/student")
    public void studentSignUp(@RequestBody @Valid StudentSignUpRequest request) {
        studentSignUpService.execute(request);
    }

    @PostMapping("/login")
    public void userLogin(@RequestBody @Valid UserLoginRequest request) {
        userLoginService.execute(request);
    }
}
