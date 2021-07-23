package biat.learning.springredditclone.services;

import biat.learning.springredditclone.domain.NotificationEmail;
import biat.learning.springredditclone.domain.Post;
import biat.learning.springredditclone.domain.User;
import biat.learning.springredditclone.exceptions.PostNotFoundException;
import biat.learning.springredditclone.repositories.CommentRepository;
import biat.learning.springredditclone.repositories.PostRepository;
import biat.learning.springredditclone.repositories.UserRepository;
import biat.learning.springredditclone.web.dto.CommentDto;
import biat.learning.springredditclone.web.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MailService mailService;
    private final UserRepository userRepository;
    private static final String POST_URL = ""; //TODO Implement logic

    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
        User currentUser = authService.getCurrentUser();
        commentRepository.save(commentMapper.map(commentDto, post, currentUser));

        String message = currentUser.getUsername() + " posted a comment on your post. " + POST_URL;
        sendCommentNotification(message, post.getUser(), currentUser);
    }

    private void sendCommentNotification(String message, User poster, User commenter) {
        mailService.sendMail(new NotificationEmail(commenter.getUsername() +
                " commented on your post", poster.getEmail(), message));
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findAllByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
