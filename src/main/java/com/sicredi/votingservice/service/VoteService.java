package com.sicredi.votingservice.service;

import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.model.entity.VoteEntity;
import com.sicredi.votingservice.model.request.VoteRequest;
import com.sicredi.votingservice.model.response.VoteResponse;
import com.sicredi.votingservice.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VoteService {

    private final TopicService topicService;
    private final SessionService sessionService;
    private final VoteRepository voteRepository;
    public void processVote(Long topicId, VoteRequest request){
        var topic = topicService.findById(topicId);
        if(topic.isPresent()){
            if(voteRepository.existsByDocumentAndTopic(request.getDocument(), topic.get())){
                throw new IllegalArgumentException("O voto já foi computado nesse tópico, utilizar outro documento válido!");
            }
            sessionService.validateIfSessionExists(topic.get());
            var vote = VoteEntity.builder().topic(topic.get()).vote(request.getVote()).document(request.getDocument()).build();
            try{
                voteRepository.save(vote);
            }catch (RuntimeException e){
                throw e;
            }
        }else {
            throw new IllegalArgumentException("O tópico de votação solicitado não existe!");
        }
    }

    public VoteResponse getTopicResult(Long topicId){
        var topic = topicService.findById(topicId);
        if(topic.isPresent()){
            var yesVotes = getAllYesVotes(topic.get());
            var noVotes = getAllNoVotes(topic.get());
            return VoteResponse.builder().topicName(topic.get().getTopicName()).yesVotes(yesVotes).noVotes(noVotes).totalVotes(yesVotes+noVotes).build();
        }
        throw new IllegalArgumentException("O tópico de votação solicitado não existe!");
    }

    public Long getAllYesVotes(TopicEntity topic){
        try{
            return voteRepository.countByTopicAndVoteTrue(topic);
        }catch (RuntimeException e){
            throw new IllegalArgumentException("O voto já foi computado nesse tópico, utilizar outro documento válido!");
        }
    }

    public Long getAllNoVotes(TopicEntity topic){
        try {
            return voteRepository.countByTopicAndVoteFalse(topic);
        }catch (RuntimeException e){
            throw new IllegalArgumentException("O voto já foi computado nesse tópico, utilizar outro documento válido!");
        }
    }
}
