package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.VoteRequest;
import com.sicredi.votingservice.model.response.VoteResponse;
import com.sicredi.votingservice.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Vote Controller", description = "Endpoints for user vote and get results of topics. ")
@Validated
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "Vote in created topic.",
            description = "Endpoint to vote on topics with opened sessions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)))})
    @PostMapping(value = "/votes/{topicId}")
    public ResponseEntity<String> vote(@NotNull @PathVariable Long topicId, @Valid @NotNull @RequestBody VoteRequest voteRequest) {
        voteService.processVote(topicId, voteRequest);
        return ResponseEntity.ok(COMPUTED_VOTE_SUCCESSFULLY);
    }

    @Operation(summary = "Return topic results.",
            description = "Endpoint to return topic results that have closed sessions of vote.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VoteResponse.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Object.class)))})
    @GetMapping(value = "/votes/{topicId}")
    public ResponseEntity<VoteResponse> result(@NotNull @PathVariable Long topicId) {
        var voteResponse = voteService.getTopicResult(topicId);
        return ResponseEntity.ok(voteResponse);
    }
}
