package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.SessionRequest;
import com.sicredi.votingservice.service.SessionService;
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

import javax.validation.constraints.NotNull;

import static com.sicredi.votingservice.constants.VotingServiceConstants.OPENING_SESSION_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Tag(name = "Session Controller", description = "Endpoint to open a session in created topic. ")
@Validated
public class SessionController {

    private final SessionService sessionService;

    @Operation(summary = "Open a session.",
            description = "Endpoint to open a session for a specific topic.")
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
    @PostMapping("/open-session/{topicId}")
    public ResponseEntity<String> openSession(@NotNull @PathVariable Long topicId,@NotNull @RequestBody SessionRequest sessionRequest) {
        sessionService.processSessionOpening(topicId, sessionRequest.getSessionEndTime());
        return ResponseEntity.ok(OPENING_SESSION_SUCCESSFULLY);
    }
}
