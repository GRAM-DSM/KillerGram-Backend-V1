package gram.killergram.domain.user.service;

import gram.killergram.domain.email.domain.Email;
import gram.killergram.domain.email.exception.InvalidVerificationCodeException;
import gram.killergram.domain.email.exception.UnauthorizedRequestException;
import gram.killergram.domain.email.repository.EmailCrudRepository;
import gram.killergram.domain.user.exception.UserAlreadyExistsException;
import gram.killergram.domain.user.presentation.dto.request.StudentSignUpRequest;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.domain.type.Authority;
import gram.killergram.domain.user.facade.UserFacade;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.user.repository.UserJpaRepository;
import gram.killergram.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentSignUpService {

    private final UserJpaRepository userJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailCrudRepository emailCrudRepository;

    @Transactional
    public void execute(StudentSignUpRequest request) {
        if (userJpaRepository.findByAccountId(request.getAccountId()).isPresent()) {
            throw UserAlreadyExistsException.EXCEPTION;
        }

        Optional<Email> email = emailCrudRepository.findById(request.getAccountId());

        if (!email.isPresent()) {
            throw InvalidVerificationCodeException.EXCEPTION;
        }

        if (!email.get().getAuthorizationStatus()) {
            throw UnauthorizedRequestException.EXCEPTION;
        }

        User user = new User(
                request.getAccountId(),
                passwordEncoder.encode(request.getPassword()),
                request.getDeviceToken(),
                Authority.STUDENT
        );
        user = userJpaRepository.save(user);


        Student student = Student.builder()
                .studentId(user.getUserId())
                .name(request.getName())
                .gender(request.getGender())
                .ability(request.getAbility())
                .schoolNumber(request.getSchoolNumber())
                .build();

        studentJpaRepository.save(student);

        emailCrudRepository.delete(email.get());
    }
}
