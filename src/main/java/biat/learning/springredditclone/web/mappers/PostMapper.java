package biat.learning.springredditclone.web.mappers;

import biat.learning.springredditclone.domain.Post;
import biat.learning.springredditclone.domain.Subreddit;
import biat.learning.springredditclone.domain.User;
import biat.learning.springredditclone.domain.Vote;
import biat.learning.springredditclone.domain.enumeration.VoteType;
import biat.learning.springredditclone.repositories.CommentRepository;
import biat.learning.springredditclone.repositories.VoteRepository;
import biat.learning.springredditclone.services.AuthService;
import biat.learning.springredditclone.web.dto.PostRequest;
import biat.learning.springredditclone.web.dto.PostResponseDto;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "postName", source = "postRequest.postName")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "voteCount", constant = "0")

    public abstract Post postRequestmapToPost(PostRequest postRequest, Subreddit subreddit, User user);


    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    @Mapping(target = "userName", source = "user.username")
    public abstract PostResponseDto mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, VoteType.UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
