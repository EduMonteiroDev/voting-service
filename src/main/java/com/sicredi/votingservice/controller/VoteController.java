package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.VoteRequest;
import com.sicredi.votingservice.model.response.VoteResponse;
import com.sicredi.votingservice.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.sicredi.votingservice.constants.VotingServiceConstants.COMPUTED_VOTE_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "/votes/{topicId}")
    public ResponseEntity<String> vote(@NotNull @PathVariable Long topicId, @Valid @RequestBody VoteRequest voteRequest) {
        voteService.processVote(topicId, voteRequest);
        return ResponseEntity.ok(COMPUTED_VOTE_SUCCESSFULLY);
    }

    @GetMapping(value = "/votes/{topicId}")
    public ResponseEntity<VoteResponse> result(@NotNull @PathVariable Long topicId) {
        var voteResponse = voteService.getTopicResult(topicId);
        return ResponseEntity.ok(voteResponse);
    }
}
