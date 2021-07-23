package biat.learning.springredditclone.services;

import biat.learning.springredditclone.domain.Post;
import biat.learning.springredditclone.domain.Subreddit;
import biat.learning.springredditclone.domain.User;
import biat.learning.springredditclone.exceptions.PostNotFoundException;
import biat.learning.springredditclone.exceptions.SubredditNotFoundException;
import biat.learning.springredditclone.repositories.PostRepository;
import biat.learning.springredditclone.repositories.SubredditRepository;
import biat.learning.springredditclone.repositories.UserRepository;
import biat.learning.springredditclone.web.dto.PostRequest;
import biat.learning.springredditclone.web.dto.PostResponseDto;
import biat.learning.springredditclone.web.mappers.PostMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j

public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
        Post savedPost = postRepository.save(postMapper.postRequestmapToPost(postRequest, subreddit, authService.getCurrentUser()));

        subreddit.getPosts().add(savedPost);
        subredditRepository.save(subreddit);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        log.info("posttest"+post.getUser().getUsername());
        log.info("postToMapperPostResponse"+postMapper.mapToDto(post));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
