package com.xc.x_clone_backend.post;

import com.xc.x_clone_backend.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "api/post")
public class PostController {
    private final PostService postService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public PostController(PostService postService, CloudinaryService cloudinaryService) {
        this.postService = postService;
        this.cloudinaryService = cloudinaryService;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Post> createPost(
            @RequestParam("content") String content,
            @RequestParam(value = "ifreply", required = false) Integer ifreply,
            // @RequestParam(value = "ifretweet", required = false) Integer ifretweet,
            @RequestPart(value = "media", required = false) MultipartFile media) {

        Post post = new Post();
        post.setContent(content);
        post.setIfReply(ifreply);
        // post.setIfretweet(ifretweet);

        if (media != null && !media.isEmpty()) {
            if (media.getSize() > 30 * 1024 * 1024) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size exceeds limit");
            }

            String contentType = media.getContentType();
            if (!isValidMediaType(contentType)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid media type");
            }

            String mediaUrl = cloudinaryService.uploadFile(media, "posts");
            post.setMedia_url(mediaUrl);
            post.setMedia_type(determineMediaType(contentType));
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        post.setUsername(username);
        post.setDate(new Date());

        Post createdPost = postService.addPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PostMapping("/retweet/{id}")
    public ResponseEntity<Post> retweet(@PathVariable Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Post originalPost = postService.getPostById(id);
        if (originalPost == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        boolean hasRetweeted = postService.getPosts().stream()
        .anyMatch(post -> 
            post.getUsername().equals(username) && 
            post.getIfretweet() != null && 
            post.getIfretweet().equals(id)
        );
        if (hasRetweeted) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already retweeted");
        }
        Post retweet = new Post();
        retweet.setContent(originalPost.getContent());
        retweet.setMedia_url(originalPost.getMedia_url());
        retweet.setMedia_type(originalPost.getMedia_type());
        retweet.setUsername(username);
        retweet.setDate(new Date());
        retweet.setIfretweet(id);  
        
        Post createdRetweet = postService.addPost(retweet);
        return new ResponseEntity<>(createdRetweet, HttpStatus.CREATED);
    }

    private boolean isValidMediaType(String contentType) {
        if (contentType == null) return false;
        return contentType.startsWith("image/") || contentType.startsWith("video/");
    }

    private String determineMediaType(String contentType) {
        if (contentType.startsWith("image/")) return "image";
        if (contentType.startsWith("video/")) return "video";
        return null;
    }

    @DeleteMapping("/{id}")
public ResponseEntity<String> deletePost(@PathVariable Integer id) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    String postname = postService.getPostById(id).getUsername();
    if (username.equals(postname)) {
        List<Post> replies = postService.getRepliesById(id);
        for (Post reply : replies) {
            postService.deletePost(reply.getPost_id());
        }
        List<Post> retweets = postService.getPosts().stream()
            .filter(post -> post.getIfretweet() != null && post.getIfretweet().equals(id))
            .toList();
        for (Post retweet : retweets) {
            postService.deletePost(retweet.getPost_id());
        }
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
    } else {
        return new ResponseEntity<>("Not authorized", HttpStatus.FORBIDDEN);
    }
}
}