package gram.killergram.domain.email.service;

import gram.killergram.domain.email.repository.EmailCrudRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteDbFalseOldService {
    private final EmailCrudRepository emailCrudRepository;

    // at 02:00 am
    @Transactional
    @Scheduled(cron = "0 0 2 * * ?")
    public void deleteExpiredEmails() {
        emailCrudRepository.deleteByCertifiedTimeBefore(LocalDateTime.now());
    }
}
