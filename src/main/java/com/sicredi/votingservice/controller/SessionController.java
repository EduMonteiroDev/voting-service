package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.SessionRequest;
import com.sicredi.votingservice.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

import static com.sicredi.votingservice.constants.VotingServiceConstants.OPENING_SESSION_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/open-session/{topicId}")
    public ResponseEntity<String> openSession(@NotBlank @PathVariable Long topicId, @RequestBody SessionRequest sessionRequest) {
        sessionService.processSessionOpening(topicId, sessionRequest.getSessionEndTime());
        return ResponseEntity.ok(OPENING_SESSION_SUCCESSFULLY);
    }
}
