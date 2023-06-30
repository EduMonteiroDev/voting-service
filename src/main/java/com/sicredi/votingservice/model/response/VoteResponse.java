package com.sicredi.votingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponse {

    private String topicName;
    private Long yesVotes;
    private Long noVotes;
    private Long totalVotes;
}
