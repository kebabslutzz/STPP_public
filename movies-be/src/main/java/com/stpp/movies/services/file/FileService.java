package com.stpp.movies.services.file;

import com.stpp.movies.entities.Poster;
import com.stpp.movies.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Validated
public class FileService {
    private final FileRepository fileRepository;

//    @Transactional
//    public Poster savePoster(MultipartFile file) throws IOException {
//        Poster poster = new Poster();
//        poster.setPoster(file.getBytes());
//        return fileRepository.save(poster);
//    }

    @Transactional
    public Long savePoster(MultipartFile file) throws IOException {
        Poster poster = new Poster();
        poster.setPoster(file.getBytes());
        poster.setTitle(file.getOriginalFilename());
        Poster savedPoster = fileRepository.save(poster);
        return savedPoster.getId();
    }

    @Transactional
    public void updatePoster(Long id, MultipartFile file) throws IOException {
        if (!fileRepository.existsById(id)) {
            throw new IllegalArgumentException("Poster with ID " + id + " not found");
        }
        Poster poster = fileRepository.findById(id).orElseThrow();
        poster.setPoster(file.getBytes());
        poster.setTitle(file.getOriginalFilename());
        fileRepository.save(poster);
    }

    @Transactional
    public Poster getPoster(Long id) {
        return fileRepository.findById(id).orElseThrow();
    }

    public boolean existsById(Long posterId) {
        return fileRepository.existsById(posterId);
    }
}
