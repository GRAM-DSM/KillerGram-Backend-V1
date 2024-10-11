package gram.killergram.domain.user.presentation;


import gram.killergram.domain.user.presentation.dto.request.*;
import gram.killergram.domain.user.service.*;
import gram.killergram.domain.user.presentation.dto.response.TokenResponse;
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
    private final PasswordResetService passwordResetService;
    private final UserExitService userExitService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void studentSignUp(@RequestBody @Valid StudentSignUpRequest request) {
        studentSignUpService.execute(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public TokenResponse userLogin(@RequestBody @Valid UserLoginRequest request) {
        return userLoginService.execute(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reset-password")
    public void resetPassword(@RequestBody @Valid PasswordResetRequest passwordResetRequest) {
        passwordResetService.resetPassword(passwordResetRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/delete-user/{UserId}")
    public void resetPassword(@PathVariable String UserId) {
        userExitService.execute(UserId);
    }
}