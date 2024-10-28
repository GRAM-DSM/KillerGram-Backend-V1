package gram.killergram.domain.vote.service;

import gram.killergram.domain.vote.domain.Vote;
import gram.killergram.domain.vote.repository.VoteCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteCloseService {

    private final VoteCrudRepository voteCrudRepository;

    @Transactional
    public void closeVote() {
        LocalDate today = LocalDate.now();
        List<Vote> votes = voteCrudRepository.findByVoteDate(today);

        for(Vote vote : votes){
            if(!vote.isEnd()) {
                vote.updateIsEnd(true);
                voteCrudRepository.save(vote);
            }
        }
    }
}
