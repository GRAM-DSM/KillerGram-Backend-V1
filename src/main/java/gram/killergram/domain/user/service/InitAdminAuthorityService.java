package gram.killergram.domain.user.service;

import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.domain.type.Ability;
import gram.killergram.domain.user.domain.type.Authority;
import gram.killergram.domain.user.domain.type.Gender;
import gram.killergram.domain.user.repository.StudentJpaRepository;
import gram.killergram.domain.user.repository.UserJpaRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitAdminAuthorityService {
    private final UserJpaRepository userRepository;
    private final StudentJpaRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void createAdminAccount() {
        try {
            log.debug("Starting admin account initialization...");  // 추가된 로그

            String adminAccountId = "kimhyuntai@ncloud.sbs";
            String adminPassword = "password1234!!";

            log.info("===== Admin Account Creation Started =====");  // 눈에 띄는 로그
            log.info("Checking for existing admin account: {}", adminAccountId);

            if (userRepository.findByAccountId(adminAccountId).isEmpty()) {
                log.info("No existing admin account found. Creating new admin account...");

                String encodedPassword = passwordEncoder.encode(adminPassword);
                log.debug("Password encoded successfully");

                User adminUser = User.builder()
                        .accountId(adminAccountId)
                        .password(encodedPassword)
                        .deviceToken("ADMIN_INITIAL_TOKEN")
                        .authority(Authority.TEACHER)
                        .build();

                adminUser = userRepository.save(adminUser);
                log.info("Admin user saved successfully. User ID: {}", adminUser.getUserId());

                Student adminStudent = Student.builder()
                        .user(adminUser)
                        .name("김현태")
                        .gender(Gender.MAN)
                        .ability(Ability.MIDDLE)
                        .schoolNumber("00000000")
                        .build();

                studentRepository.save(adminStudent);
                log.info("Admin student profile created successfully");
                log.info("===== Admin Account Creation Completed =====");
            } else {
                log.info("Admin account already exists. Skipping creation.");
            }
        } catch (Exception e) {
            log.error("!!!!! Failed to initialize admin account !!!!!", e);
            log.error("Error message: {}", e.getMessage());
            log.error("Stack trace: ", e);  // 스택 트레이스 전체 출력
            throw new RuntimeException("Failed to initialize admin account: " + e.getMessage(), e);
        }
    }
}