package biat.learning.springredditclone.boostrap;

import biat.learning.springredditclone.domain.Post;
import biat.learning.springredditclone.domain.Subreddit;
import biat.learning.springredditclone.domain.User;
import biat.learning.springredditclone.repositories.PostRepository;
import biat.learning.springredditclone.repositories.SubredditRepository;
import biat.learning.springredditclone.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.Instant;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubredditLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;






    @Override
    public void run(String... args) throws Exception {
        if(subredditRepository.count()==0){
            loadsubreddit();
        }
    }

    private void loadsubreddit() {
        Optional<User> lists =userRepository.findById(1L);
        User user1=lists.get();
        List<Post> posts=postRepository.findAll();

        Subreddit subreddit=Subreddit.builder().
                name("Performance Difference Between save() and saveAll() in Spring Data")
                .description("In this quick tutorial, we'll learn about the performance difference between save() and saveAll() methods in Spring Data.")
                .posts(posts)
                .createdDate(Instant.now())
                .user(user1).build();

        Subreddit subreddit_second=Subreddit.builder().
                name("Performance Difference Between save() and saveAll() in Spring Data")
                .description("In this quick tutorial, we'll learn about the performance difference between save() and saveAll() methods in Spring Data.")
                .posts(posts)
                .createdDate(Instant.now())
                .user(user1).build();

        Subreddit subreddit_tree=Subreddit.builder().
                name("dzdzd dzdz Between save() and dzd() in Spring Data")
                .description("In this quick tutorial, dzdzdzdzd'll learn about the performance difference between save() and saveAll() methods in Spring Data.")
                .posts(posts)
                .createdDate(Instant.now())
                .user(user1).build();
        subredditRepository.save(subreddit);
        subredditRepository.save(subreddit_second);
        subredditRepository.save(subreddit_tree);


    }
}
