package gram.killergram.domain.vote.domain;

import gram.killergram.domain.sport.domain.Sport;
import gram.killergram.domain.vote.domain.type.Day;
import gram.killergram.domain.vote.domain.type.TimeSlot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "vote")
@Getter
@NoArgsConstructor
@Table(name = "vote_tbl")
public class Vote {

    @Id
    @Column(name = "vote_id", unique = true, nullable = false)
    private UUID voteId;

    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate;

    @Column(name = "participate", nullable = false)
    private Integer participate;

    @Column(name = "is_end", nullable = false)
    private boolean isEnd;

    @OneToOne
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sportId;

    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "time_slot", nullable = false)
    private String timeSlot;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<VoteUser> voteUser = new ArrayList<>();

    @Builder
    public Vote(LocalDate voteDate, Integer participate, boolean isEnd, Sport sportId, String day, String timeSlot) {
        this.sportId = sportId;
        this.voteId = UUID.randomUUID();
        this.voteDate = voteDate;
        this.participate = participate;
        this.isEnd = isEnd;
        this.day = day;
        this.timeSlot = timeSlot;
    }

    public Day getDay() {
        return Day.fromValue(day);
    }

    public TimeSlot getTimeSlot() {
        return TimeSlot.fromValue(timeSlot);
    }
}
