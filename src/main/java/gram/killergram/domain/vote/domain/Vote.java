package gram.killergram.domain.vote.domain;

import gram.killergram.domain.sport.domain.Sport;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "vote")
@Getter
@NoArgsConstructor
@Table(name = "vote_tbl")
public class Vote {

    @Id
    @Column(name = "vote_id",unique = true, nullable = false)
    private UUID voteId;

    @Column(name = "vote_date", nullable = false)
    private String voteDate;

    @Column(name = "vote_position", nullable = true)
    private Integer votePosition;

    @Column(name = "participate", nullable = false)
    private Integer participate;

    @Column(name = "is_end", nullable = false)
    private boolean isEnd;

    @OneToOne
    @JoinColumn(name = "sport_id",nullable = false)
    private Sport sportId;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL)
    private List<VoteUser> voteUser = new ArrayList<>();

    @Builder
    public Vote(String voteDate, Integer votePosition, Integer participate, boolean isEnd) {
        this.voteId = UUID.randomUUID();
        this.voteDate = voteDate;
        this.votePosition = votePosition;
        this.participate = participate;
        this.isEnd = isEnd;
    }
}
