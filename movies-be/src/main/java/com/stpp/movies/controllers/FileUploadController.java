package com.stpp.movies.controllers;

import com.stpp.movies.dto.ErrorResponseDto;
import com.stpp.movies.dto.file.FileResponseDto;
import com.stpp.movies.services.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/files")
@Tag(name = "File Upload Operations", description = "Controller that uploads, gets and updates a file")
public class FileUploadController {
  private final FileService fileService;

  @Operation(summary = "Get a poster", description = "Get a poster for a movie", responses = {
    @ApiResponse(responseCode = "200", description = "OK"),
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @GetMapping("/{fileId}")
  public FileResponseDto getPoster(@PathVariable Long fileId) {
    return fileService.getPoster(fileId);
  }

  @Operation(summary = "Upload a poster", description = "Upload a poster for a movie", responses = {
    @ApiResponse(responseCode = "201", description = "Created"),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = "multipart/form-data")
  public FileResponseDto uploadPoster(@RequestParam("file") MultipartFile file) {
    return fileService.savePoster(file);
  }

  @Operation(summary = "Update a poster", description = "Update a poster for a movie", responses = {
    @ApiResponse(responseCode = "200", description = "OK"),
    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @PutMapping(value = "/{fileId}", consumes = "multipart/form-data")
  public FileResponseDto updatePoster(@PathVariable Long fileId, @RequestParam("file") MultipartFile file) {
    return fileService.updatePoster(fileId, file);
  }
}
