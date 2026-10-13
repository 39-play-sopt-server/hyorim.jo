package org.sopt.post.dto;

public record CreatePostRequest(
        String category,
        String title,
        String content,
        String author
) {

}
