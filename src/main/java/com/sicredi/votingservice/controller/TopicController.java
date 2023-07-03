package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.TopicRequest;
import com.sicredi.votingservice.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.sicredi.votingservice.constants.VotingServiceConstants.CREATE_TOPIC_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "Topic Controller", description = "Endpoint to create a new topic.")
@Validated
public class TopicController {

    private final TopicService topicService;

    @Operation(summary = "Create new topic.",
            description = "Endpoint to creation new vote topic.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = "application/json"))})
    @PostMapping("/topic-voting")
    public ResponseEntity<String> createTopic(@Valid @NotNull @RequestBody TopicRequest topicRequest) {
        topicService.insertNewTopic(topicRequest.getTopicName());
        return ResponseEntity.ok(CREATE_TOPIC_SUCCESSFULLY);
    }
}
