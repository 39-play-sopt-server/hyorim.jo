package org.sopt.post.repository;

import org.sopt.post.domain.Category;
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

    public List<Post> findPage(long offset, int size, Category category) {
        return posts.values().stream()
                .filter(post -> category == null || post.getCategory() == category) // 카테고리 필수 조건 x
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
