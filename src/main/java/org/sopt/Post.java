package org.sopt;

public class Post {
    private String title;
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    // 함수 하나로 제목과 내용 수정 모두에 대응하기 위해서 게시글을 수정하는 함수는 Post 단위로 작성
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}