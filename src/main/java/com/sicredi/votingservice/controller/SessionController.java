package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.SessionRequest;
import com.sicredi.votingservice.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.sicredi.votingservice.constants.VotingServiceConstants.OPENING_SESSION_SUCCESSFULLY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
@Validated
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/open-session/{topicId}")
    public ResponseEntity<String> openSession(@NotNull @PathVariable Long topicId, @RequestBody SessionRequest sessionRequest) {
        sessionService.processSessionOpening(topicId, sessionRequest.getSessionEndTime());
        return ResponseEntity.ok(OPENING_SESSION_SUCCESSFULLY);
    }
}
