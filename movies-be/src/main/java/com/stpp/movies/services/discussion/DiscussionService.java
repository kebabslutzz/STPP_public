package com.stpp.movies.services.discussion;

import com.stpp.movies.dto.discussion.DiscussionEditRequestDto;
import com.stpp.movies.dto.discussion.DiscussionRequestDto;
import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.entities.Discussion;
import com.stpp.movies.exceptions.NotFoundException;
import com.stpp.movies.repositories.CommentRepository;
import com.stpp.movies.repositories.DiscussionRepository;
import com.stpp.movies.repositories.MovieRepository;
import com.stpp.movies.repositories.UserRepository;
import com.stpp.movies.services.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Validated
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private static final DiscussionMapper MAPPER = DiscussionMapper.INSTANCE;

    @Transactional
    public List<DiscussionResponseDto> getAllDiscussionsByMovieId(Long movieId) {
        return discussionRepository
                .findAllByMovieId(movieId)
                .stream()
                .map(discussion -> {
                    int commentCount = commentService.getNumberOfCommentsByDiscussionId(discussion.getId());
                    DiscussionResponseDto discussionResponseDto = MAPPER.discussionToResponseDto(discussion);
                    discussionResponseDto.setCommentCount(commentCount);
                    return discussionResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<DiscussionResponseDto> getDiscussionById(Long id) {
        Optional<Discussion> discussion = discussionRepository.findById(id);
        return discussion.map(discussion1 -> {
            int commentCount = commentService.getAllByDiscussionId(discussion1.getId()).size();
            DiscussionResponseDto discussionResponseDto = MAPPER.discussionToResponseDto(discussion1);
            System.out.println("Discussion ID: " + discussion1.getId() + ", Comment Count: " + commentCount); // logging

            discussionResponseDto.setCommentCount(commentCount);
            return discussionResponseDto;
        });
    }

    @Transactional
    public Optional<DiscussionResponseDto> getDiscussionByIMovieAndDiscussiond(Long movieId, Long discussionId){
        if (!discussionRepository.existsById(discussionId)) {
            throw new NotFoundException("Discussion with ID " + discussionId + " not found");
        }
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie with ID " + movieId + " not found");
        }
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);
        return discussion.map(discussion1 -> {
            int commentCount = commentService.getAllByDiscussionId(discussion1.getId()).size();
            DiscussionResponseDto discussionResponseDto = MAPPER.discussionToResponseDto(discussion1);
            System.out.println("Discussion ID: " + discussion1.getId() + ", Comment Count: " + commentCount); // logging
            discussionResponseDto.setCommentCount(commentCount);
            return discussionResponseDto;
        });
    }

    @Transactional
    public List<DiscussionResponseDto> getAllDiscussions() {
        return discussionRepository
                .findAllByOrderByMovieAsc()
                .stream()
                .map(discussion -> {
                    int commentCount = commentService.getNumberOfCommentsByDiscussionId(discussion.getId());
                    DiscussionResponseDto discussionResponseDto = MAPPER.discussionToResponseDto(discussion);
                    discussionResponseDto.setCommentCount(commentCount);
                    return discussionResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public DiscussionResponseDto editDiscussion(@Valid Long movieId, @Valid Long discussionId, @Valid DiscussionEditRequestDto discussionEditRequestDto) {
        if (!discussionRepository.existsById(discussionId)) {
            throw new NotFoundException("Discussion with ID " + discussionId + " not found");
        }
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie with ID " + movieId + " not found");
        }
        Discussion discussion = discussionRepository.findById(discussionId).get();
        MAPPER.discussionEditRequestDtoToDiscussion(discussionEditRequestDto, discussion);
        return MAPPER.discussionToResponseDto(discussion);
    }

    @Transactional
    public void deleteDiscussionById(Long id) {
        if (!discussionRepository.existsById(id)) {
            throw new NotFoundException("Discussion with ID " + id + " not found");
        }
        discussionRepository.deleteById(id);
    }

    @Transactional
    public void deleteDiscussionById(Long movieId, Long discussionId) {
        if (!movieRepository.existsById(movieId)) {
            throw new NotFoundException("Movie with ID " + movieId + " not found");
        }
        if (!discussionRepository.existsById(discussionId)) {
            throw new NotFoundException("Discussion with ID " + discussionId + " not found");
        }
        discussionRepository.deleteById(discussionId);
    }

    @Transactional
    public DiscussionResponseDto createDiscussion(Long id, DiscussionRequestDto discussionRequestDto) {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException("Movie with ID " + id + " not found");
        }
        if (!userRepository.existsById(discussionRequestDto.getUserId())) {
            throw new NotFoundException("User with ID " + discussionRequestDto.getUserId() + " not found");
        }

        Discussion discussion = MAPPER.discussionRequestDtoToDiscussion(discussionRequestDto);

        discussion = discussionRepository.save(discussion);
        return MAPPER.discussionToResponseDto(discussion);
    }
}
