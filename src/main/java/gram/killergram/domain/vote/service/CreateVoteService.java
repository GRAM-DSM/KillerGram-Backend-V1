package gram.killergram.domain.vote.service;

import gram.killergram.domain.sport.domain.Sport;
import gram.killergram.domain.sport.domain.type.SportName;
import gram.killergram.domain.user.domain.Student;
import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.domain.type.Day;
import gram.killergram.domain.vote.domain.type.TimeSlot;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import gram.killergram.domain.sport.repository.SportCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class CreateVoteService {

    private final VoteCrudRepository voteCrudRepository;
    private final SportCrudRepository sportCrudRepository;

    @Transactional
    public void execute(String managerEmail, SportName sportName, Integer personnel, Boolean isPosition) {
        Sport sport = Sport.builder()
                .sportName(sportName)
                .isPosition(isPosition)
                .personnel(personnel)
                .managerEmail(managerEmail)
                .build();
        sportCrudRepository.save(sport);

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        TimeSlot timeSlot = currentTime.isBefore(LocalTime.of(13, 30))
                ? TimeSlot.LAUNCH_TIME
                : TimeSlot.DINNER_TIME;

        Day day = Day.valueOf(currentDate.getDayOfWeek().name());

        Vote vote = Vote.builder()
                .voteDate(currentDate)
                .day(day)
                .timeSlot(timeSlot)
                .isEnd(false)
                .participate(0)
                .sportId(sport)
                .build();

        voteCrudRepository.save(vote);
    }
}
