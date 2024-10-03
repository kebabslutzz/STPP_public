package com.stpp.movies.controllers;

import com.stpp.movies.dto.file.FileRequestDto;
import com.stpp.movies.dto.file.FileResponseDto;
import com.stpp.movies.entities.Poster;
import com.stpp.movies.services.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/files")
public class FileUploadController {
    private final FileService fileService;

//    @PostMapping
//    public ResponseEntity<Long> uploadPoster(@RequestParam("file") MultipartFile file) {
//        Long posterId = fileService.savePoster(file);
//        return new ResponseEntity<>(posterId, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<FileResponseDto> uploadPoster(@RequestParam("file") MultipartFile file) {
        FileResponseDto fileResponseDto = fileService.savePoster(file);
        return new ResponseEntity<>(fileResponseDto, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<String> getPoster(@PathVariable Long id) {
//        Poster poster = fileService.getPoster(id);
//        String base64Poster = Base64.getEncoder().encodeToString(poster.getPoster());
//        return ResponseEntity.ok(base64Poster);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<FileResponseDto> getPoster(@PathVariable Long id) {
        FileResponseDto fileResponseDto = fileService.getPoster(id);
        return ResponseEntity.ok(fileResponseDto);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<String> updatePoster(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        fileService.updatePoster(id, file);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<FileResponseDto> updatePoster(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        FileResponseDto fileResponseDto = fileService.updatePoster(id, file);
        return new ResponseEntity<>(fileResponseDto, HttpStatus.OK);
    }
}
