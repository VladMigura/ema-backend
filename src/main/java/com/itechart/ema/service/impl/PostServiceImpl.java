package com.itechart.ema.service.impl;

import com.itechart.ema.entity.UserEntity;
import com.itechart.ema.exception.NotFoundException;
import com.itechart.ema.repository.PostRepository;
import com.itechart.ema.service.PostService;
import com.itechart.ema.service.UserService;
import com.itechart.generated.model.RestPost;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.itechart.ema.exception.Constants.POST_NOT_FOUND;
import static com.itechart.ema.exception.Constants.USER_NOT_FOUND;
import static com.itechart.ema.mapper.PostMapper.POST_MAPPER;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(final UUID postId) {
        return postRepository.existsById(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public void postExistsOrException(final UUID postId) {
        if (!existsById(postId)) {
            throw new NotFoundException(POST_NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public RestPost getPostById(final UUID postId) {
        return postRepository.findOneById(postId)
                .map(POST_MAPPER::toRestPost)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestPost> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(POST_MAPPER::toRestPost)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestPost> getUserPosts(final UUID userId) {
        if (!userService.existsById(userId)) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        return postRepository.findAllByUserId(userId)
                .stream()
                .map(POST_MAPPER::toRestPost)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RestPost createPost(final RestPost post) {
        var entity = POST_MAPPER.toPostEntity(post);
        entity.setAuthor(UserEntity.builder().id(post.getAuthor().getId()).build());
        var saved = postRepository.saveAndFlush(entity);
        return POST_MAPPER.toRestPost(saved);
    }

    @Override
    @Transactional
    public RestPost updatePost(final RestPost post, final UUID postId) {
        var existing = postRepository.findOneById(postId)
                .orElseThrow(() -> new NotFoundException(POST_NOT_FOUND));
        var updated = POST_MAPPER.updateEntity(post, existing);
        return POST_MAPPER.toRestPost(postRepository.saveAndFlush(updated));
    }

    @Override
    @Transactional
    public void deletePost(final UUID postId) {
        postExistsOrException(postId);
        postRepository.softDeleteOneById(postId);
    }

}
