package org.sopt;

import org.sopt.post.controller.PostController;
import org.sopt.post.repository.PostRepository;
import org.sopt.post.service.PostService;

public class Server {
    private final PostController controller;

    public Server() {
        PostRepository repository = new PostRepository();
        PostService service = new PostService(repository);
        this.controller = new PostController(service);
    }

    public PostController controller() {
        return controller;
    }
}