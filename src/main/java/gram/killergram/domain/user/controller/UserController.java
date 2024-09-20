package gram.killergram.domain.user.controller;

import gram.killergram.domain.user.controller.dto.request.StudentSignUpRequest;
import gram.killergram.domain.user.controller.dto.request.UserLoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final StudentSignUpRequest studentSignUpService;
    private final UserLoginRequest userLoginService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/student")
    public void studentSignUp(@RequestBody @Valid StudentSignUpRequest request) {

    }

    @PostMapping("/login")
    public void userLogin(@RequestBody @Valid UserLoginRequest request) {

    }
}
