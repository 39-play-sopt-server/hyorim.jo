package org.sopt.client;

import org.sopt.global.response.ApiResponse;
import org.sopt.post.dto.PostResponse;

import java.util.List;
import java.util.Scanner;

public class PostView {
    private final Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    public int readCommand() {
        System.out.print("선택: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void printCategory() {
        System.out.println("\n=== 카테고리 ===");
        System.out.println("레시피");
        System.out.println("운동");
        System.out.println("IT");
    }

    public String readCategory() {
        System.out.print("카테고리: ");
        return scanner.nextLine();
    }

    public String readTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String readContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public String readAuthor() {
        System.out.print("작성자: ");
        return scanner.nextLine();
    }

    public int readPostNumber(String message) {
        System.out.print(message);
        return Integer.parseInt(scanner.nextLine());
    }

    public void printPosts(ApiResponse<List<PostResponse>> posts) {
        if (!posts.isSuccess()) {
            printMessage(posts);
            return;
        }

        System.out.println("\n=== 게시글 목록 ===");
        for (PostResponse post : posts.data()) {
            System.out.println(post.id() + ". " + post.title());
        }
    }

    public void printPost(ApiResponse<PostResponse> post) {
        if (!post.isSuccess()) {
            printMessage(post);
            return;
        }

        System.out.println("\n=== 게시글 ===");
        System.out.println("카테고리: " + post.data().category());
        System.out.println("제목: " + post.data().title());
        System.out.println("내용: " + post.data().content());
        System.out.println("작성자: " + post.data().author());
    }

    public void printMessage(ApiResponse<?> message) {
        System.out.println(message.message());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
