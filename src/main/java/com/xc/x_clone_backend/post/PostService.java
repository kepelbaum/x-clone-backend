    package com.xc.x_clone_backend.post;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Component;

    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;

    @Component
    public class PostService {
        private final PostRepository postRepository;

        @Autowired
        public PostService(PostRepository postRepository) {
            this.postRepository = postRepository;
        }

        public List<Post> getPosts() {
            return postRepository.findAll();
        }

        public List<Post> getPostsByUser(String user) {
            return postRepository.findAll().stream()
                    .filter(post -> user.equals(post.getUsername()) && post.getIfreply() == null)
                    .collect(Collectors.toList());
        }

        public Post getPostById(Integer id) {
            return postRepository.findAll().stream()
                    .filter(post -> id.equals(post.getPost_id()))
                    .findFirst().orElse(null);
        }

        public List<Post> getRepliesById(Integer id) {
            return postRepository.findAll().stream()
                    .filter(post -> id.equals(post.getIfreply()))
                    .collect(Collectors.toList());
        }

        public Post addPost(Post post) {
            postRepository.save(post);
            return post;
        }

    //    public Post updatePost(Post updatedPost) {
    //        Optional<Post> existingPost = postRepository.findByPostId(updatedPost.getPost_id());
    //
    //        if (existingPost.isPresent()) {
    //            Post postToUpdate = existingPost.get();
    //            postToUpdate.setUsername(updatedPost.getUsername());
    //
    //        }
    //    }
        public void deletePost(Integer id) {
            postRepository.deleteById(id);
        }
    }
