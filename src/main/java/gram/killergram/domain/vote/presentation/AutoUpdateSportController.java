package gram.killergram.domain.vote.presentation;

import gram.killergram.domain.sport.domain.type.SportName;
import gram.killergram.domain.vote.service.CreateVoteService;
import gram.killergram.domain.vote.service.VoteCloseService;
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
    private final VoteCloseService voteCloseService;

    // default Admin email
    @Value("${email.gram}")
    private String defaultEmail;

    // at 12:35 pm and 17(5):35 pm
    // Find today day of the week, and find correct Sport fit for day of the week
    @Scheduled(cron = "0 35 12,17 * * *")
    public void delegateDay() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();

        Map<DayOfWeek, List<Object[]>> sportVoteMap = Map.of(
                DayOfWeek.MONDAY, List.of(
                        new Object[]{SportName.BADMINTON, 20, false},
                        new Object[]{SportName.SOCCER, 18, false}
                ),
                DayOfWeek.TUESDAY, List.of(
                        new Object[]{SportName.BASKETBALL, 14, false},
                        new Object[]{SportName.SOCCER, 18, false}
                ),
                DayOfWeek.WEDNESDAY, List.of(
                        new Object[]{SportName.VOLLEYBALL, 18, true},
                        new Object[]{SportName.SOCCER, 18, false}
                ),
                DayOfWeek.THURSDAY, List.of(
                        new Object[]{SportName.WOMAN_SPORTS, 20, false},
                        new Object[]{SportName.SOCCER, 18, false}
                )
        );

        List<Object[]> sportVotes = sportVoteMap.get(dayOfWeek);

        if (sportVotes != null) {
            // if Today Friday or Saturday or Sunday this block not work
            for (Object[] sportVote : sportVotes) {
                createVoteService.execute(defaultEmail, (SportName) sportVote[0], (int) sportVote[1], (boolean) sportVote[2]);
            }
        }
    }

    // at 12:55 pm and 17(5):55 pm
    @Scheduled(cron = "0 27 12,17 * * *")
    public void closeVote() {
        voteCloseService.closeVote();
    }
}
