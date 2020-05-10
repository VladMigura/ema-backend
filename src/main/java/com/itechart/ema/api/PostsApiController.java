package com.itechart.ema.api;

import com.itechart.ema.service.PostService;
import com.itechart.generated.api.PostsApi;
import com.itechart.generated.model.RestPost;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
public class PostsApiController implements PostsApi {

    private final PostService postService;

    @Override
    public ResponseEntity<RestPost> createPost(@Valid @RequestBody final RestPost body) {
        var post = postService.createPost(body);
        return new ResponseEntity<>(post, CREATED);
    }

    @Override
    public ResponseEntity<Void> deletePost(@PathVariable("postId") final UUID postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<RestPost>> getAllPosts() {
        var posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, OK);
    }

    @Override
    public ResponseEntity<RestPost> getPostById(@PathVariable("postId") final UUID postId) {
        var post = postService.getPostById(postId);
        return new ResponseEntity<>(post, OK);
    }

    @Override
    public ResponseEntity<RestPost> updatePost(@Valid @RequestBody final RestPost body,
                                               @PathVariable("postId") final UUID postId) {
        var post = postService.updatePost(body, postId);
        return new ResponseEntity<>(post, OK);
    }

}
