package biat.learning.springredditclone.services;

import biat.learning.springredditclone.domain.Post;
import biat.learning.springredditclone.domain.Vote;
import biat.learning.springredditclone.domain.enumeration.VoteType;
import biat.learning.springredditclone.exceptions.PostNotFoundException;
import biat.learning.springredditclone.exceptions.SpringRedditException;
import biat.learning.springredditclone.repositories.PostRepository;
import biat.learning.springredditclone.repositories.VoteRepository;
import biat.learning.springredditclone.web.dto.VoteDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {


    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(voteDto.getPostId().toString()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            log.info("je suis ici");
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
