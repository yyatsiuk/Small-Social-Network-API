package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.dto.request.VoteAddRequest;
import com.yyatsiuk.social.sonet.dto.response.VoteAddResponse;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.vote.Vote;
import com.yyatsiuk.social.sonet.model.vote.VotePrimaryKey;
import com.yyatsiuk.social.sonet.model.vote.VoteResult;
import com.yyatsiuk.social.sonet.repository.VoteRepo;
import com.yyatsiuk.social.sonet.service.ChoiceService;
import com.yyatsiuk.social.sonet.service.PollService;
import com.yyatsiuk.social.sonet.service.UserService;
import com.yyatsiuk.social.sonet.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteServiceImpl implements VoteService {

    private VoteRepo voteRepo;
    private ChoiceService choiceService;
    private UserService userService;
    private PollService pollService;

    @Autowired
    public VoteServiceImpl(VoteRepo voteRepo, ChoiceService choiceService,
                           UserService userService,
                           PollService pollService) {
        this.voteRepo = voteRepo;
        this.choiceService = choiceService;
        this.userService = userService;
        this.pollService = pollService;
    }

    private List<VoteResult> getPercentsAndVariant (Map<Long, Long> countingVariants,
                                                    Integer countOfVariants) {
        long countOfAllUsersWhichVote = countingVariants
                .values()
                .stream()
                .mapToLong(Long::intValue).sum();
        return countingVariants.entrySet().stream()
                .map(e -> new VoteResult(e.getKey(), e.getValue(),
                        Math.round((e.getValue()*100)/countOfAllUsersWhichVote)))
                .collect(Collectors.toList());
    }

    @Override
    public Vote saveVote(VoteAddRequest voteAddRequest) {
        Vote vote = new Vote();
        vote.setPoll(pollService.findById(voteAddRequest.getPollId()));
        vote.setUser(userService.getUserById(voteAddRequest.getUserId()));
        vote.setChoice(choiceService.findById(voteAddRequest.getChoiceId()));
        return voteRepo.save(vote);
    }

    @Override
    public VoteAddResponse vote (VoteAddRequest voteAddRequest) {
        Vote savedVote = saveVote(voteAddRequest);
        Map<Long, Long> choiceWithCountingVote = voteRepo.findVariantsWithCountOfVote(voteAddRequest.getPollId()).stream()
                .collect(Collectors.toMap(el -> el[0] , el -> el[1]));
        VoteAddResponse voteAddResponse = new VoteAddResponse();
        voteAddResponse.setPercents(getPercentsAndVariant(choiceWithCountingVote, savedVote.getPoll().getChoices().size()));
        voteAddResponse.setUserChoice(voteAddRequest.getChoiceId());
        return voteAddResponse;
    }

    @Override
    public void deleteById(Long userId, Long pollId) throws EntityNotFoundException {
        VotePrimaryKey id = new VotePrimaryKey(userId, pollId);
        try {
            voteRepo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Vote with id:{0} not found", id);
        }
    }

    @Override
    public Boolean isChoiceExist (Long userId, Long pollId) {
        return voteRepo.existsVoteByPollIdAndUserId(pollId, userId);
    }

    @Override
    public VoteAddResponse findVoteByUserIdAndPollId(Long userId, Long pollId) throws EntityNotFoundException{
        Vote vote = voteRepo.findByUserIdAndPollId(userId, pollId);
        Map<Long, Long> choiceWithCountingVote = voteRepo.findVariantsWithCountOfVote(pollId).stream()
                    .collect(Collectors.toMap(el -> el[0] , el -> el[1]));
        VoteAddResponse voteAddResponse = new VoteAddResponse();
        voteAddResponse.setPercents(getPercentsAndVariant(choiceWithCountingVote, vote.getPoll().getChoices().size()));
        voteAddResponse.setUserChoice(vote.getChoice().getId());
        return voteAddResponse;
    }

}
