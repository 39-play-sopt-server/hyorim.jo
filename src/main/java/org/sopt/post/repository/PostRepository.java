package org.sopt.post.repository;

import org.sopt.community.domain.Prefix;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByPrefix(Prefix prefix, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Post p SET p.viewCount = p.viewCount + 1
        WHERE p.id = :postId
        """)
    void increaseViewCount(@Param("postId") Long postId);
}
