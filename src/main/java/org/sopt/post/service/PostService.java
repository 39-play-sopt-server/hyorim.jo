package org.sopt.post.service;

import org.sopt.post.domain.Post;
import org.sopt.post.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성
    public void createPost(String title, String content) {
        postRepository.save(new Post(title, content));
    }

    // 게시글 목록 조회
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    // 게시글 상세 조회
    public Post getPost(int postId) {
        if (postRepository.findById(postId) == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        return postRepository.findById(postId);
    }

    // 게시글 수정
    public void updatePost(int postId, String title, String content) {
        if (postRepository.findById(postId) == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        postRepository.findById(postId).updatePost(title, content);
    }

    // 게시글 삭제
    public void deletePost(int postId) {
        if (postRepository.findById(postId) == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        postRepository.deleteById(postId);
    }
}
