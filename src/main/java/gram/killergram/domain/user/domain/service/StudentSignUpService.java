package gram.killergram.domain.user.domain.service;

import gram.killergram.domain.user.exception.UserAlreadyExitsException;
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

@Service
@RequiredArgsConstructor
public class StudentSignUpService {

    private final UserJpaRepository userJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void execute(StudentSignUpRequest request) {
        if (userJpaRepository.findByAccountId(request.getAccountId()).isPresent()) {
            throw UserAlreadyExitsException.EXCEPTION;
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
    }
}
