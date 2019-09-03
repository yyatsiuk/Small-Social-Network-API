package com.yyatsiuk.social.sonet.rest;

import com.yyatsiuk.social.sonet.dto.model.PollDTO;
import com.yyatsiuk.social.sonet.dto.request.PollAddRequest;
import com.yyatsiuk.social.sonet.exception.EntityNotFoundException;
import com.yyatsiuk.social.sonet.model.Poll;
import com.yyatsiuk.social.sonet.service.PollService;
import com.yyatsiuk.social.sonet.utils.CustomModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/polls")
public class PollController {

    private final PollService pollService;

    private final CustomModelMapper<Poll, PollDTO> pollCustomModelMapper;

    @Autowired
    public PollController(
            PollService pollService,
            @Qualifier("pollMapper") CustomModelMapper<Poll, PollDTO> pollCustomModelMapper) {
        this.pollService = pollService;
        this.pollCustomModelMapper = pollCustomModelMapper;
    }

    @PostMapping
    public ResponseEntity<PollDTO> createPoll(@Valid @RequestBody PollAddRequest poll) {
        return new ResponseEntity<>(pollCustomModelMapper.toDTO(pollService.create(poll)), HttpStatus.OK);
    }

    @DeleteMapping("/{pollId}")
    public ResponseEntity deletePoll(@PathVariable Long pollId) throws EntityNotFoundException {
        pollService.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{pollId}")
    public ResponseEntity<PollDTO> getPollById(@PathVariable Long pollId) throws EntityNotFoundException {
        return new ResponseEntity<>(pollCustomModelMapper.toDTO(pollService.findById(pollId)), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<PollDTO>> getPollByPostId(@PathVariable Long postId) throws EntityNotFoundException {
        List<PollDTO> pollResponse = pollService.findAllByPostId(postId).stream()
                .map(pollCustomModelMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(pollResponse, HttpStatus.OK);
    }

}
