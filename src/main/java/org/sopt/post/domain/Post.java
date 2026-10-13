package org.sopt.post.domain;

public class Post {
    private int id;
    private Category category;
    private String title;
    private String content;
    private String author;

    public Post(Category category, String title, String content, String author) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Category getCategory() {
        return category;
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

    public String getAuthor() {
        return author;
    }

    // Post 수정은 제목과 내용을 모두 입력 받는다.
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}