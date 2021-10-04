package biat.learning.springredditclone.boostrap;

import biat.learning.springredditclone.domain.Post;
import biat.learning.springredditclone.domain.Subreddit;
import biat.learning.springredditclone.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
@RequiredArgsConstructor
public class PostLoader implements CommandLineRunner {

    private final PostRepository postRepository;
    @Override
    public void run(String... args) throws Exception {
        if(postRepository.count() !=0){
            loadPostData();
        }
    }

    private void loadPostData() {
        Post post= Post.builder().postName("https://github.com/ngxs/store")
                .createdDate(Instant.now())
                .description("support chore(docs): add module federation doc link")
                .voteCount(0).build();
        Post post2= Post.builder().postName("https://github.com/ngxsfffff/store")
                .createdDate(Instant.now())
                .description("support (docs): add module federation doc link")
                .voteCount(0).build();
        postRepository.save(post);
        postRepository.save(post2);

    }
}
