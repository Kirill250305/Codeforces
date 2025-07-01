package ru.itmo.wp.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    @Query(value = "SELECT * FROM post WHERE id=?1", nativeQuery = true)
    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public void writeComment(Post post, User user, Comment comment) {
        List<Comment> comments = post.getComments();
        comment.setUser(user);
        comment.setPost(post);
        comments.add(comment);
        postRepository.save(post);
    }
}
