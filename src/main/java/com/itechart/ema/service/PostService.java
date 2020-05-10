package com.itechart.ema.service;

import com.itechart.generated.model.RestPost;

import java.util.List;
import java.util.UUID;

public interface PostService {

    boolean existsById(UUID postId);

    void postExistsOrException(UUID postId);

    RestPost getPostById(UUID postId);

    List<RestPost> getAllPosts();

    List<RestPost> getUserPosts(UUID userId);

    RestPost createPost(RestPost post);

    RestPost updatePost(RestPost post, UUID postId);

    void deletePost(UUID postId);

}
