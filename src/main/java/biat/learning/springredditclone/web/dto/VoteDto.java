package biat.learning.springredditclone.web.dto;

import biat.learning.springredditclone.domain.enumeration.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

    private Long postId;
    private VoteType voteType;
}
