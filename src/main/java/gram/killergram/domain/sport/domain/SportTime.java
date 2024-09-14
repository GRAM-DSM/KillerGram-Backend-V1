package gram.killergram.domain.sport.domain;

import gram.killergram.domain.sport.domain.type.Day;
import gram.killergram.domain.sport.domain.type.TimeSlot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity(name = "sport_time_tbl")
@Getter
@NoArgsConstructor
public class SportTime {

    @Id
    @Column(name = "time_id",unique = true, nullable = false)
    private UUID timeId;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private Sport sportId;

    @Column(name = "day", nullable = false, columnDefinition = "VACHAR(30)")
    private Day day;

    @Column(name = "time_slot", nullable = false, columnDefinition = "VACHAR(20)")
    private TimeSlot timeSlot;

    @Builder
    public SportTime(Day day, TimeSlot timeSlot) {
        this.day = day;
        this.timeSlot = timeSlot;
    }
}