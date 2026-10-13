package org.sopt.post.repository;

import org.sopt.post.domain.Post;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostRepository {
    private final Map<Integer, Post> posts = new LinkedHashMap<>();
    private int sequence = 0;

    public Post save(Post post) {
        post.setId(autoIncrementId());
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() {
        return List.copyOf(posts.values());
    }

    public Optional<Post> findById(int postId) {
        return Optional.ofNullable(posts.get(postId));
    }

    public void delete(Post post) {
        posts.remove(post.getId());
    }

    private int autoIncrementId() {
        return ++sequence;
    }
}
