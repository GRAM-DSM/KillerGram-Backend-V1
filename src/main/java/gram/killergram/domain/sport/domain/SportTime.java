package gram.killergram.domain.sport.domain;

import gram.killergram.domain.sport.domain.type.Day;
import gram.killergram.domain.sport.domain.type.TimeSlot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity(name = "sport_time")
@Getter
@NoArgsConstructor
@Table(name = "sport_time_tbl")
public class SportTime {

    @Id
    @Column(name = "time_id", unique = true, nullable = false)
    private UUID timeId;

    @ManyToOne
    @JoinColumn(name = "sport_id", nullable = false)
    private Sport sport;

    @Column(name = "day", nullable = false, columnDefinition = "VARCHAR(30)")
    private Day day;

    @Column(name = "time_slot", nullable = false, columnDefinition = "VARCHAR(20)")
    private TimeSlot timeSlot;

    @Builder
    public SportTime(Day day, TimeSlot timeSlot) {
        this.timeId = UUID.randomUUID();
        this.day = day;
        this.timeSlot = timeSlot;
    }
}

