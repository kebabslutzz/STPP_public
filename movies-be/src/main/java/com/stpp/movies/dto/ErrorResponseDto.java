package com.stpp.movies.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(String message, int status) {
}
