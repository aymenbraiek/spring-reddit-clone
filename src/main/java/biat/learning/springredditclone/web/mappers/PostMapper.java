package biat.learning.springredditclone.web.mappers;

import biat.learning.springredditclone.domain.Post;
import biat.learning.springredditclone.domain.Subreddit;
import biat.learning.springredditclone.domain.User;
import biat.learning.springredditclone.web.dto.PostRequest;
import biat.learning.springredditclone.web.dto.PostResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostMapper {


    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "voteCount", constant = "0")
    Post postRequestmapToPost(PostRequest postRequest, Subreddit subreddit, User user);


    @Mapping(target = "postId", source = "postId")
    @Mapping(target ="postName",source = "postName")

    PostResponseDto mapToDto(Post post);
}
