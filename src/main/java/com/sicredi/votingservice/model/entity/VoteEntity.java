package com.sicredi.votingservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @OneToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    private String document;

    private Boolean vote;
}
