package org.sopt.post.dto;

public record UpdatePostRequest(
        String title,
        String content
) {
}
