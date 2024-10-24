package gram.killergram.domain.vote.presentation;

import gram.killergram.domain.sport.domain.type.SportName;
import gram.killergram.domain.vote.service.CreateVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AutoUpdateSportController {
    private final CreateVoteService createVoteService;

    @Value("${email.gram}")
    private String defaultEmail;

//    @Scheduled(cron = "0 35 12,17 * * *")
    @Scheduled(cron = "0 38 22 * * *")
    public void delegateDay() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        Map<DayOfWeek, List<Object[]>> sportVoteMap = Map.of(
                DayOfWeek.MONDAY, List.of(
                        new Object[]{SportName.BADMINTON, 20, false},
                        new Object[]{SportName.SOCCER, 16, false}
                ),
                DayOfWeek.TUESDAY, List.of(
                        new Object[]{SportName.BASKETBALL, 14, false},
                        new Object[]{SportName.SOCCER, 16, false}
                ),
                DayOfWeek.WEDNESDAY, List.of(
                        new Object[]{SportName.VOLLEYBALL, 18, true},
                        new Object[]{SportName.SOCCER, 16, false}
                ),
                DayOfWeek.THURSDAY, List.of(
                        new Object[]{SportName.WOMAN_SPORTS, 20, false},
                        new Object[]{SportName.SOCCER, 18, false}
                )
        );

        List<Object[]> sportVotes = sportVoteMap.get(dayOfWeek);

        if (sportVotes != null) {
            for (Object[] sportVote : sportVotes) {
                createVoteService.execute(defaultEmail, (SportName) sportVote[0], (int) sportVote[1], (boolean) sportVote[2]);
            }
        }
    }
}
