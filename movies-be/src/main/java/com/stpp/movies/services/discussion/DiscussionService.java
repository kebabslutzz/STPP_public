package com.stpp.movies.services.discussion;

import com.stpp.movies.dto.discussion.DiscussionResponseDto;
import com.stpp.movies.repositories.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Validated
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private static final DiscussionMapper MAPPER = DiscussionMapper.INSTANCE;

    public List<DiscussionResponseDto> getAllDiscussionsByMovieId(Long movieId) {
        return discussionRepository
                .findAllByMovieId(movieId)
                .stream()
                .map(MAPPER::discussionToResponseDto)
                .collect(Collectors.toList());
    }
}
