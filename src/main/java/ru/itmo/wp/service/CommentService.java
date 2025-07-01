package ru.itmo.wp.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> findAll() {
        return commentRepository.findAllByOrderByCreationTimeDesc();
    }

    @Query(value = "SELECT * FROM comment WHERE id=?1", nativeQuery = true)
    public Comment findById(Long id) {
        return id == null ? null : commentRepository.findById(id).orElse(null);
    }
}
