package com.sicredi.votingservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "topic")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long topicId;

    @OneToOne(mappedBy = "topic", cascade = CascadeType.ALL)
    private SessionEntity session;

    @Column(name = "topic_name")
    private String topicName;

}
