package biat.learning.springredditclone.web.controller;

import biat.learning.springredditclone.services.PostService;
import biat.learning.springredditclone.web.dto.PostRequest;
import biat.learning.springredditclone.web.dto.PostResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponseDto>> getPostsBySubreddit(Long id) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }

}
