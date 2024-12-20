package gram.killergram.domain.vote.domain;

import gram.killergram.domain.sport.domain.Sport;
import gram.killergram.domain.vote.domain.type.Day;
import gram.killergram.domain.vote.domain.type.TimeSlot;
import gram.killergram.global.exception.AmolangEguTuzimuenTextAdminGo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
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
    private Set<VoteUser> voteUser = new LinkedHashSet<>();

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

    public void increaseParticipate() {
        this.participate++;
    }

    public void decreaseParticipate() {
        if(this.participate - 1 < 0)
            throw AmolangEguTuzimuenTextAdminGo.EXCEPTION; /* First of all, this code cannot be executed
            due to server logic, but I made an exception just in case */
        this.participate--;
    }

    public void addVoteUser(VoteUser voteUser) {
        this.voteUser.add(voteUser);
    }

    public void removeVoteUser(VoteUser voteUser) {
        voteUser.setVote(null);
        this.voteUser.remove(voteUser);
    }

    public Set<VoteUser> getVoteUser() {
        return new LinkedHashSet<>(voteUser);
    }

    public void updateIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
}
