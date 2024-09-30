package com.stpp.movies.services.comment;

import com.stpp.movies.dto.comment.CommentRequestDto;
import com.stpp.movies.dto.comment.CommentResponseDto;
import com.stpp.movies.entities.Comment;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.CommentRepository;
import com.stpp.movies.repositories.DiscussionRepository;
import com.stpp.movies.repositories.MovieRepository;
import com.stpp.movies.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;

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

    public List<CommentResponseDto> getAllByDiscussionId(Long id) {
        return commentRepository
                .findAllByDiscussionId(id)
                .stream()
                .map(MAPPER::commentToResponseDto)
                .collect(java.util.stream.Collectors.toList());
    }

    @Transactional
    public CommentResponseDto createComment(Long movieId, Long discussionId, CommentRequestDto content) {
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie with ID " + movieId + " not found");
        }
        if (!discussionRepository.existsById(discussionId)) {
            throw new NotFoundException("Discussion with ID " + discussionId + " not found");
        }
        if (!userRepository.existsById(content.getUserId())) {
            throw new NotFoundException("User with ID " + content.getUserId() + " not found");
        }

        Comment comment = MAPPER.requestDtoToComment(content, discussionId);
        comment = commentRepository.save(comment);
        return MAPPER.commentToResponseDto(comment);
    }

    public List<CommentResponseDto> getAllCommentsByDiscussionId(Long movieId, Long discussionId) {
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie with ID " + movieId + " not found");
        }
        if (!discussionRepository.existsById(discussionId)) {
            throw new NotFoundException("Discussion with ID " + discussionId + " not found");
        }
        return getAllByDiscussionId(discussionId);
    }
}
