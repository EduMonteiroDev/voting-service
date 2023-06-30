package com.sicredi.votingservice.service;

import com.sicredi.votingservice.exception.BusinessServiceException;
import com.sicredi.votingservice.exception.DatabaseException;
import com.sicredi.votingservice.model.entity.VoteEntity;
import com.sicredi.votingservice.model.request.VoteRequest;
import com.sicredi.votingservice.model.response.VoteResponse;
import com.sicredi.votingservice.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sicredi.votingservice.constants.VotingServiceConstants.ILLEGAL_VOTE_EXCEPTION;
import static com.sicredi.votingservice.constants.VotingServiceConstants.TOPIC_EXIST_EXCEPTION;


@Service
@RequiredArgsConstructor
public class VoteService {

    private final TopicService topicService;
    private final SessionService sessionService;
    private final VoteRepository voteRepository;
    public void processVote(Long topicId, VoteRequest request){
        var topic = topicService.findById(topicId)
                .orElseThrow(() -> new BusinessServiceException(TOPIC_EXIST_EXCEPTION));

        if (voteRepository.existsByDocumentAndTopic(request.getDocument(), topic)) {
            throw new BusinessServiceException(ILLEGAL_VOTE_EXCEPTION);
        }
        sessionService.validateIfSessionExists(topic);

        var voteEntity = VoteEntity.builder()
                .topic(topic)
                .vote(request.getVote())
                .document(request.getDocument())
                .build();
        try{
            voteRepository.save(voteEntity);
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }

    public VoteResponse getTopicResult(Long topicId){
        var topic = topicService.findById(topicId)
                .orElseThrow(() -> new BusinessServiceException(TOPIC_EXIST_EXCEPTION));
        try {
            long yesVotes = voteRepository.countByTopicAndVoteTrue(topic);
            long noVotes = voteRepository.countByTopicAndVoteFalse(topic);

            return VoteResponse.builder()
                    .topicName(topic.getTopicName())
                    .yesVotes(yesVotes)
                    .noVotes(noVotes)
                    .totalVotes(yesVotes + noVotes)
                    .build();
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }
}
