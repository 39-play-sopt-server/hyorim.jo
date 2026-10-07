package org.sopt.client;

import org.sopt.Server;
import org.sopt.post.controller.PostController;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        PostController controller = new Server().controller();

        while (true) {
            view.printMenu();
            int command = view.readCommand();

            switch (command) {
                case 1 -> {
                    view.printMessage(controller.createPost(view.readTitle(), view.readContent()));
                }
                case 2 -> {
                    view.printPosts(controller.getPosts());
                }
                case 3 -> {
                    view.printPost(controller.getPost(view.readPostNumber("조회할 게시글 번호: ")));
                }
                case 4 -> {
                    view.printMessage(controller.updatePost(view.readPostNumber("수정할 게시글 번호: "), view.readTitle(), view.readContent()));
                }
                case 5 -> {
                    view.printMessage(controller.deletePost(view.readPostNumber("삭제할 게시글 번호: ")));
                }
                case 6 -> {
                    view.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> view.printMessage("잘못된 입력입니다.");
            }
        }
    }
}