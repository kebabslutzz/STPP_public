package com.stpp.movies.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/upload")
public class FileUploadController {
    private final FileService fileService;

//    @PostMapping
//    public ResponseEntity<Long> uploadPoster(@RequestParam("file") MultipartFile file) {
//        try {
//            Poster poster = fileService.savePoster(file);
//            return new ResponseEntity<>(poster.getId(), HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping
    public ResponseEntity<Long> uploadPoster(@RequestParam("file") MultipartFile file) {
        try {
            Long posterId = fileService.savePoster(file);
            return new ResponseEntity<>(posterId, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getPoster(@PathVariable Long id) {
        Poster poster = fileService.getPoster(id);
        String base64Poster = Base64.getEncoder().encodeToString(poster.getPoster());
        return ResponseEntity.ok(base64Poster);
//        return ResponseEntity.ok(poster.getPoster());
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<String> updatePoster(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        try {
//            Poster poster = fileService.getPoster(id);
//            poster.setPoster(file.getBytes());
//            fileService.savePoster(file);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePoster(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            fileService.updatePoster(id, file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
