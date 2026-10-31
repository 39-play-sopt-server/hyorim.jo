package org.sopt.post.dto;

import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public record PostPageResponse(
        List<PostListResponse> posts,
        int page,
        int size,
        long totalPosts,
        int totalPages,
        boolean hasNext
) {
    public static PostPageResponse from(Page<Post> page) {
        return new PostPageResponse(
                page.getContent().stream().map(PostListResponse::from).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext()
        );
    }
}