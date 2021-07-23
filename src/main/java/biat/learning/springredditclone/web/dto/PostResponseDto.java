package biat.learning.springredditclone.web.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostResponseDto {

    private Long postId;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String subredditName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;

}
