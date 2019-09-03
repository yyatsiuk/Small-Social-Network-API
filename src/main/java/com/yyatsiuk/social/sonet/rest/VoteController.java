package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.request.VoteAddRequest;
import com.yyatsiuk.social.sonet.dto.response.VoteAddResponse;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("api/votes")
public class VoteController {

    private VoteService voteService;

    @Autowired
    VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<VoteAddResponse> vote(@Valid @RequestBody VoteAddRequest voteAddRequest) {
        return new ResponseEntity<>(voteService.vote(voteAddRequest), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteVote(@RequestParam("user_id")Long userId, @RequestParam("poll_id")Long pollId)
            throws EntityNotFoundException {
        voteService.deleteById(userId, pollId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/exist")
    public ResponseEntity<Boolean> isExistVote(@RequestParam("user_id")Long userId, @RequestParam("poll_id")Long pollId) {
        return new ResponseEntity<>(voteService.isChoiceExist(userId, pollId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<VoteAddResponse> getVoteByUserIdAndPollId(@RequestParam("user_id")Long userId, @RequestParam("poll_id")Long pollId) {
        return new ResponseEntity<>(voteService.findVoteByUserIdAndPollId(userId, pollId), HttpStatus.OK);
    }

}
