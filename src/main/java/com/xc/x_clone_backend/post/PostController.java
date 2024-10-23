package com.xc.x_clone_backend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getPosts(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer post_id) {
        if (post_id != null) {
            return postService.getRepliesById(post_id);
        }
        else if (username != null) {
            return postService.getPostsByUser(username);
        }
        else {
            return postService.getPosts();
        }
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        post.setUsername(username);
        post.setDate(new Date());
        Post createdPost = postService.addPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

//    @PutMapping
//    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
//        Post resultPost = postService.updatePost(post);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String postname = postService.getPostById(id).getUsername();
        if (username.equals(postname)) {
            postService.deletePost(id);
            List<Post> replies = postService.getRepliesById(id);
            for (Post reply : replies) {
                postService.deletePost(reply.getPost_id());
            }
            return new ResponseEntity<>("Post deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
