package gram.killergram.domain.vote.presentation.dto.response;

import gram.killergram.domain.sport.domain.type.SportName;
import gram.killergram.domain.user.domain.type.Ability;
import gram.killergram.domain.vote.domain.VoteUser;
import gram.killergram.domain.vote.domain.type.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinSocketVoteResponse {

    private SportName sportName;

    private Ability ability;

    private Set<VoteUser> voteStudents;

    private TimeSlot timeSlot;

    private Integer participate;

    private boolean isJoinMe;

    private boolean isEnd;

    private boolean isAdmin;
}
