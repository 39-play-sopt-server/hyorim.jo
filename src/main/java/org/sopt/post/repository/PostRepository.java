package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new LinkedHashMap<>();
    private Long sequence = 0L;

    public Post save(Post post) {
        post.setId(autoIncrementId());
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() {
        return List.copyOf(posts.values());
    }

    public List<Post> findPage(long offset, int size) {
        return posts.values().stream()
            .skip(offset)
            .limit(size)
            .toList();
    }

    public Optional<Post> findById(Long postId) {
        return Optional.ofNullable(posts.get(postId));
    }

    public void delete(Post post) {
        posts.remove(post.getId());
    }

    private Long autoIncrementId() {
        return ++sequence;
    }
}
