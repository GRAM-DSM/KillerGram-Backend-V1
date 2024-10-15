package gram.killergram.domain.vote.presentation;

import gram.killergram.domain.sport.domain.type.SportName;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.vote.service.CreateVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoUpdateSportController {
    private final CreateVoteService createVoteService;

    @Scheduled(cron = "0 0 10 * * ?")
    public void scheduleCreateVoteAt10AM() {
        createVoteService.execute("bakbak@test.cc", SportName.SOCCER, 10, false);
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleCreateVoteAt2PM() {
        createVoteService.execute("bakbak@test.cc", SportName.SOCCER, 10, false);
    }
}
