package org.sopt.post.dto;

import org.sopt.post.domain.Category;
import org.sopt.post.domain.Post;

public record PostResponse(
        int id,
        Category category,
        String title,
        String content,
        String author
) {
    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getCategory(), post.getTitle(), post.getContent(), post.getAuthor());
    }
}
