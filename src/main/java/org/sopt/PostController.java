package org.sopt;

import java.util.ArrayList;
import java.util.List;

public class PostController {
    private final List<Post> posts = new ArrayList<>();
    private final PostView view;

    public PostController(PostView view) {
        this.view = view;
    }

    public void run() {
        while (true) {
            view.printMenu();
            int command = view.readCommand();
            switch (command) {
                case 1 -> postPost();
                case 2 -> getPosts();
                case 3 -> getPost();
                case 4 -> patchPost();
                case 5 -> deletePost();
                case 6 -> {
                    view.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> view.printMessage("잘못된 입력입니다.");
            }
        }
    }

    // 게시글 작성
    public void postPost() {
        String title = view.readTitle();
        String content = view.readContent();
        posts.add(new Post(title, content));
        view.printMessage("게시글이 작성되었습니다.");
    }

    // 게시글 목록 조회
    private void getPosts() {
        if (posts.isEmpty()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }
        for (int i = 0; i < posts.size(); i++) {
            view.printMessage((i + 1) + ". " + posts.get(i).getTitle());
        }
    }

    // 게시글 상세 조회
    private void getPost() {
        if (posts.isEmpty()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        int index = view.readPostNumber("조회할 게시글 번호: ") - 1;

        if (!isValidIndex(index)) {
            view.printMessage("존재하지 않는 게시글입니다.");
            return;
        }
        Post post = posts.get(index);
        view.printPost(post);
    }

    // 게시글 수정
    public void patchPost() {
        if (posts.isEmpty()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        int index = view.readPostNumber("수정할 게시글 번호: ") - 1;

        if (!isValidIndex(index)) {
            view.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        String newTitle = view.readTitle();
        String newContent = view.readContent();

        Post post = posts.get(index);

        post.updatePost(newTitle, newContent);

        view.printMessage("게시글이 수정되었습니다.");
    }

    // 게시글 삭제
    private void deletePost() {
        if (posts.isEmpty()) {
            view.printMessage("게시글이 없습니다.");
            return;
        }

        int index = view.readPostNumber("삭제할 게시글 번호: ") - 1;

        if (!isValidIndex(index)) {
            view.printMessage("존재하지 않는 게시글입니다.");
            return;
        }

        posts.remove(index);

        view.printMessage("게시글이 삭제되었습니다.");
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < posts.size();
    }
}
