package com.Msoftwares.Twitter.Clone.Controllers;

import com.Msoftwares.Twitter.Clone.Model.Post;
import com.Msoftwares.Twitter.Clone.Model.User;
import com.Msoftwares.Twitter.Clone.Service.PostService;
import com.Msoftwares.Twitter.Clone.Service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    public PostController(PostService postService, UserService
            userService) {
        this.postService = postService;
        this.userService = userService;
    }
    @PostMapping
    public Mono<Post> save(Principal principal, @RequestBody Post
            post) {
        Mono<User> user =
                userService.getUserByScreenName(principal.getName());
        return user.flatMap(u -> {
            post.setUserPost(u);
            return postService.save(post);
        });
    }
    @GetMapping
    public Flux<Post> getAll(Principal principal) {
        return postService.getRelevantPosts(principal.getName());
    }
}
