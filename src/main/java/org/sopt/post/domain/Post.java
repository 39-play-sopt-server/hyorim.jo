package org.sopt.post.domain;

public class Post {
    private int id;
    private String title;
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    // Post 수정은 제목과 내용을 모두 입력 받는다.
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}