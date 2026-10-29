package org.sopt.post.service;

import org.sopt.global.exception.GeneralException;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.*;
import org.sopt.post.repository.CategoryRepository;
import org.sopt.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    // 게시글 작성
    @Transactional
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

        Category postCategory = categoryRepository.findByName(request.category())
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_INVALID_CATEGORY));

        return PostResponse.from(postRepository.save(new Post(postCategory, request.title(), request.content(), request.author())));
    }

    // 게시글 목록 조회
    public PostPageResponse getPosts(int page, int size, String category) {
        if (page < 0 || size < 1) {
            throw new GeneralException(PostErrorCode.POST_INVALID_PAGE_REQUEST);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return PostPageResponse.from(findPosts(category, pageable));
    }

    private Page<Post> findPosts(String category, Pageable pageable) {
        // ?category= 빈 문자열 처리
        if (category == null || category.isBlank()) {
            return postRepository.findAll(pageable);
        }

        Category postCategory = categoryRepository.findByName(category)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_INVALID_CATEGORY));

        return postRepository.findByCategory(postCategory, pageable);
    }

    // 게시글 상세 조회
    @Transactional
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

        post.increaseViewCount();

        return PostResponse.from(post);
    }

    // 게시글 수정
    @Transactional
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
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }
}
