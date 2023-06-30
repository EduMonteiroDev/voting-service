package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.VoteRequest;
import com.sicredi.votingservice.model.response.VoteResponse;
import com.sicredi.votingservice.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static com.sicredi.votingservice.constants.VotingServiceConstants.COMPUTED_VOTE_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "/votes/{topicId}")
    public ResponseEntity<String> vote(@NotBlank @PathVariable Long topicId,@Valid @RequestBody VoteRequest voteRequest) {
        voteService.processVote(topicId, voteRequest);
        return ResponseEntity.ok(COMPUTED_VOTE_SUCCESSFULLY);
    }

    @GetMapping(value = "/votes/{topicId}")
    public ResponseEntity<VoteResponse> result(@NotBlank @PathVariable Long topicId) {
        return ResponseEntity.ok(voteService.getTopicResult(topicId));
    }
}
