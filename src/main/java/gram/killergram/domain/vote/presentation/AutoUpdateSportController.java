package gram.killergram.domain.vote.presentation;

import gram.killergram.domain.sport.domain.type.SportName;
import gram.killergram.domain.vote.service.CreateVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AutoUpdateSportController {
    private final CreateVoteService createVoteService;

    @Scheduled(cron = "0 35 12,17 * * *")
    public void delegateDay(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        if(dayOfWeek == DayOfWeek.MONDAY) {
            createVoteService.execute("gram2024dsm@gmail.com", SportName.BADMINTON, 20, false);
            createVoteService.execute("gram2024dsm@gmail.com", SportName.SOCCER, 16, false);
        }
        if(dayOfWeek == DayOfWeek.TUESDAY) {
            createVoteService.execute("gram2024dsm@gmail.com", SportName.BASKETBALL, 14, false);
            createVoteService.execute("gram2024dsm@gmail.com", SportName.SOCCER, 16, false);
        }
        if(dayOfWeek == DayOfWeek.WEDNESDAY) {
            createVoteService.execute("gram2024dsm@gmail.com", SportName.VOLLEYBALL, 18, true);
            createVoteService.execute("gram2024dsm@gmail.com", SportName.SOCCER, 16, false);
        }
        if(dayOfWeek == DayOfWeek.THURSDAY) {
            createVoteService.execute("gram2024dsm@gmail.com", SportName.WOMAN_SPORTS, 20, false);
            createVoteService.execute("gram2024dsm@gmail.com", SportName.SOCCER, 18, false);
        }
    }
}
