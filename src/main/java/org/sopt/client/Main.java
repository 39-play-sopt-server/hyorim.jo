package org.sopt.client;

import org.sopt.post.controller.PostController;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.PostService;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        PostRepository repository = new PostRepository();
        PostService service = new PostService(repository);
        PostController controller = new PostController(service);

        while (true) {
            view.printMenu();
            int command = view.readCommand();

            switch (command) {
                case 1 -> {
                    controller.createPost(view.readTitle(), view.readContent());
                    view.printMessage("게시글이 작성되었습니다.");
                }
                case 2 -> {
                    view.printPosts(controller.getPosts());
                }
                case 3 -> {
                    view.printPost(controller.getPost(view.readPostNumber("조회할 게시글 번호: ")));
                }
                case 4 -> {
                    controller.updatePost(view.readPostNumber("수정할 게시글 번호: "), view.readTitle(), view.readContent());
                    view.printMessage("게시글이 수정되었습니다.");
                }
                case 5 -> {
                    controller.deletePost(view.readPostNumber("삭제할 게시글 번호: "));
                    view.printMessage("게시글이 삭제되었습니다.");
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