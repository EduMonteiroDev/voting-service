package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.TopicRequest;
import com.sicredi.votingservice.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TopicController {

    private final TopicService topicService;

    @PostMapping("/topic-voting")
    public ResponseEntity<String> createTopic(@Valid @RequestBody TopicRequest topicRequest) {
        topicService.insertNewTopic(topicRequest.getTopicName());
        return ResponseEntity.ok("Tópico de votação criado com sucesso!");
    }
}
