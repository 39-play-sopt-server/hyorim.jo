package org.sopt.post.service;

import org.sopt.global.exception.GeneralException;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성
    public PostResponse createPost(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new GeneralException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (content == null || content.isBlank()) {
            throw new GeneralException(PostErrorCode.POST_CONTENT_EMPTY);
        }

        return PostResponse.from(postRepository.save(new Post(title, content)));
    }

    // 게시글 목록 조회
    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream().map(PostResponse::from).toList();
    }

    // 게시글 상세 조회
    public PostResponse getPost(int postId) {
        if (postRepository.findById(postId) == null) {
            throw new GeneralException(PostErrorCode.POST_NOT_FOUND);
        }

        return PostResponse.from(postRepository.findById(postId));
    }

    // 게시글 수정
    public void updatePost(int postId, String title, String content) {
        if (postRepository.findById(postId) == null) {
            throw new GeneralException(PostErrorCode.POST_NOT_FOUND);
        }

        if (title == null || title.isBlank()) {
            throw new GeneralException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (content == null || content.isBlank()) {
            throw new GeneralException(PostErrorCode.POST_CONTENT_EMPTY);
        }

        postRepository.findById(postId).updatePost(title, content);
    }

    // 게시글 삭제
    public void deletePost(int postId) {
        if (postRepository.findById(postId) == null) {
            throw new GeneralException(PostErrorCode.POST_NOT_FOUND);
        }

        postRepository.deleteById(postId);
    }
}
