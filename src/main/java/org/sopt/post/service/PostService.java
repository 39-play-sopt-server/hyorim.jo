package org.sopt.post.service;

import org.sopt.community.code.CommunityErrorCode;
import org.sopt.community.domain.Board;
import org.sopt.community.domain.Community;
import org.sopt.community.domain.CommunityProfile;
import org.sopt.community.domain.Prefix;
import org.sopt.community.repository.BoardRepository;
import org.sopt.community.repository.CommunityProfileRepository;
import org.sopt.community.repository.PrefixRepository;
import org.sopt.global.code.CommonErrorCode;
import org.sopt.global.exception.GeneralException;
import org.sopt.member.code.MemberErrorCode;
import org.sopt.member.domain.Profile;
import org.sopt.member.repository.ProfileRepository;
import org.sopt.post.code.PostErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.*;
import org.sopt.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final PrefixRepository prefixRepository;
    private final CommunityProfileRepository communityProfileRepository;
    private final ProfileRepository profileRepository;

    public PostService(PostRepository postRepository, BoardRepository boardRepository, PrefixRepository prefixRepository, CommunityProfileRepository communityProfileRepository, ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.prefixRepository = prefixRepository;
        this.communityProfileRepository = communityProfileRepository;
        this.profileRepository = profileRepository;
    }

    // 게시글 작성
    @Transactional
    public PostResponse createPost(Long memberId, CreatePostRequest request) {
        if (request.boardId() == null) {
            throw new GeneralException(PostErrorCode.POST_BOARD_EMPTY);
        }
        if (request.profileId() == null) {
            throw new GeneralException(PostErrorCode.POST_AUTHOR_EMPTY);
        }
        if (request.title() == null || request.title().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_TITLE_EMPTY);
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new GeneralException(PostErrorCode.POST_CONTENT_EMPTY);
        }

        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new GeneralException(CommunityErrorCode.BOARD_NOT_FOUND));
        Community community = board.getCategory().getCommunity();

        CommunityProfile author = findAuthor(memberId, request.profileId(), community);

        Prefix prefix = findPrefixInCommunity(request.prefixId(), community);

        Post post = new Post(board, prefix, author, request.title(), request.content());
        return PostResponse.from(postRepository.save(post));
    }

    // 회원은 카페에 여러 프로필로 가입할 수 있기 때문에 현재 선택한 프로필을 찾는다.
    private CommunityProfile findAuthor(Long memberId, Long profileId, Community community) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(MemberErrorCode.PROFILE_NOT_FOUND));
        if (!profile.getMember().getId().equals(memberId)) {
            throw new GeneralException(MemberErrorCode.PROFILE_NOT_OWNED);
        }

        return communityProfileRepository.findByCommunityAndProfile(community, profile)
                .filter(communityProfile -> !communityProfile.isDeleted())
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_COMMUNITY_MEMBER));
    }

    // 말머리(선택)
    private Prefix findPrefixInCommunity(Long prefixId, Community community) {
        if (prefixId == null) {
            return null;
        }

        Prefix prefix = prefixRepository.findById(prefixId)
                .orElseThrow(() -> new GeneralException(CommunityErrorCode.PREFIX_NOT_FOUND));
        if (!prefix.getCommunity().getId().equals(community.getId())) {
            throw new GeneralException(PostErrorCode.POST_INVALID_PREFIX);
        }
        return prefix;
    }

    // 게시글 목록 조회
    public PostPageResponse getPosts(int page, int size, Long prefixId) {
        if (page < 0 || size < 1) {
            throw new GeneralException(CommonErrorCode.INVALID_PAGE_REQUEST);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return PostPageResponse.from(findPosts(prefixId, pageable));
    }

    private Page<Post> findPosts(Long prefixId, Pageable pageable) {
        if (prefixId == null) {
            return postRepository.findAll(pageable);
        }

        Prefix prefix = prefixRepository.findById(prefixId)
                .orElseThrow(() -> new GeneralException(CommunityErrorCode.PREFIX_NOT_FOUND));

        return postRepository.findByPrefix(prefix, pageable);
    }

    // 게시글 상세 조회
    @Transactional
    public PostResponse getPost(Long postId) {
        postRepository.increaseViewCount(postId);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(PostErrorCode.POST_NOT_FOUND));

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
