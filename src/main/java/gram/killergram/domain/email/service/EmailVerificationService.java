package gram.killergram.domain.email.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> codeExpiration = new ConcurrentHashMap<>();
    private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(5);

    @Transactional
    public boolean verifyEmail(String email, String code) {
        Long expirationTime = codeExpiration.get(email);
        if (expirationTime == null || System.currentTimeMillis() > expirationTime) {
            return false;
        }

        String storedCode = verificationCodes.get(email);
        if (storedCode != null && storedCode.equals(code)) {
            verificationCodes.remove(email);
            codeExpiration.remove(email);
            return true;
        }
        return false;
    }

    @Transactional
    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
        codeExpiration.put(email, System.currentTimeMillis() + EXPIRATION_TIME);
    }
}
