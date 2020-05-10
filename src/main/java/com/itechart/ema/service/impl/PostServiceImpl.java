package com.itechart.ema.service.impl;

import com.itechart.ema.service.PostService;
import com.itechart.generated.model.RestPost;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Override
    @Transactional(readOnly = true)
    public RestPost getPostById(final UUID postId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestPost> getAllPosts() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestPost> getUserPosts(final UUID userId) {
        return null;
    }

    @Override
    @Transactional
    public RestPost createPost(final RestPost post) {
        return null;
    }

    @Override
    @Transactional
    public RestPost updatePost(final RestPost post, final UUID postId) {
        return null;
    }

    @Override
    @Transactional
    public void deletePost(final UUID postId) {

    }

}
