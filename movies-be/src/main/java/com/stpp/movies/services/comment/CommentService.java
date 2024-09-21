package com.stpp.movies.services.comment;

import com.stpp.movies.dto.comment.CommentEditRequestDto;
import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.entities.Comment;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.CommentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Validated
public class CommentService {

    private final CommentRepository commentRepository;

    private static final CommentMapper MAPPER = CommentMapper.INSTANCE;

    @Transactional
    public Optional<CommentResponseDto> getCommentById(@Valid Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(MAPPER::commentToResponseDto);
    }

    @Transactional
    public List<CommentResponseDto> getAllComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(MAPPER::commentToResponseDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public CommentResponseDto editComment(@Valid Long commentId, @Valid String content){
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment with ID " + commentId + " not found");
        }
        Comment comment = commentRepository.findById(commentId).get();
        MAPPER.commentEditRequestDtoToComment(content, comment);
        return MAPPER.commentToResponseDto(comment);
    }

    @Transactional
    public void deleteComment(@Valid Long id){
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comment with ID " + id + " not found");
        }
        commentRepository.deleteById(id);
    }

//    @Transactional
//    public CommentResponseDto createComment(@Valid String content){
//        Comment comment = MAPPER.requestDtoToComment(content);
//        comment = commentRepository.save(comment);
//        return MAPPER.commentToResponseDto(comment);
//    }
}
