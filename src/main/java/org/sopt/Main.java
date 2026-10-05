package org.sopt;

public class Main {

    public static void main(String[] args) {
        PostView view = new PostView();
        PostController controller = new PostController(view);
        controller.run();
    }
}