package org.sopt.post.service;

import org.sopt.global.exception.GeneralException;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.CreatePostRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.UpdatePostRequest;
import org.sopt.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 작성
    public PostResponse createPost(CreatePostRequest request) {
        if (request.category() == null || request.category().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_INVALID_CATEGORY);
        }
        if (request.title() == null || request.title().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_CONTENT_EMPTY);
        }
        if (request.author() == null || request.author().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_AUTHOR_EMPTY);
        }

        Category postCategory;
        try {
            postCategory = Category.valueOf(request.category().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new GeneralException(PostErrorCode.POST_INVALID_CATEGORY);
        }

        return PostResponse.from(postRepository.save(new Post(postCategory, request.title(), request.content(), request.author())));
    }

    // 게시글 목록 조회
    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream().map(PostResponse::from).toList();
    }

    // 게시글 상세 조회
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));
        return PostResponse.from(post);
    }

    // 게시글 수정
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

        if (request.title() == null || request.title().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_CONTENT_EMPTY);
        }

        post.updatePost(request.title(), request.content());
        return PostResponse.from(post);
    }

    // 게시글 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }
}
