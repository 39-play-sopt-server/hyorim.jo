package org.sopt.post.repository;

import org.sopt.post.domain.Post;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PostRepository {
    private final Map<Integer, Post> posts = new LinkedHashMap<>();
    private int sequence = 0;

    public void save(Post post) {
        post.setId(autoIncrementId());
        posts.put(post.getId(), post);
    }

    public List<Post> findAll() {
        return List.copyOf(posts.values());
    }

    public Post findById(int postId) {
        return posts.get(postId);
    }

    public void deleteById(int postId) {
        posts.remove(postId);
    }

    private int autoIncrementId() {
        return ++sequence;
    }
}
