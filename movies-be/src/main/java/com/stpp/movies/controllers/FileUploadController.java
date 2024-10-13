package com.stpp.movies.controllers;

import com.stpp.movies.dto.file.FileResponseDto;
import com.stpp.movies.services.file.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/files")
public class FileUploadController {
  private final FileService fileService;

  @Operation(summary = "Upload a poster", description = "Upload a poster for a movie", responses = {
    @ApiResponse(responseCode = "201", description = "Created")
  })
  @PostMapping
  public ResponseEntity<FileResponseDto> uploadPoster(@RequestParam("file") MultipartFile file) {
    FileResponseDto fileResponseDto = fileService.savePoster(file);
    return new ResponseEntity<>(fileResponseDto, HttpStatus.CREATED);
  }

  @Operation(summary = "Get a poster", description = "Get a poster for a movie", responses = {
    @ApiResponse(responseCode = "200", description = "OK")
  })
  @GetMapping("/{id}")
  public ResponseEntity<FileResponseDto> getPoster(@PathVariable Long id) {
    FileResponseDto fileResponseDto = fileService.getPoster(id);
    return ResponseEntity.ok(fileResponseDto);
  }

  @Operation(summary = "Update a poster", description = "Update a poster for a movie", responses = {
    @ApiResponse(responseCode = "200", description = "OK")
  })
  @PutMapping("/{id}")
  public ResponseEntity<FileResponseDto> updatePoster(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    FileResponseDto fileResponseDto = fileService.updatePoster(id, file);
    return new ResponseEntity<>(fileResponseDto, HttpStatus.OK);
  }
}
