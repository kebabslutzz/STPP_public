package com.stpp.movies.services.file;

import com.stpp.movies.dto.file.FileResponseDto;
import com.stpp.movies.entities.Poster;
import com.stpp.movies.exceptions.FileStorageException;
import com.stpp.movies.exceptions.NotFoundException;
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
    private static final FileMapper MAPPER = FileMapper.INSTANCE;

//    @Transactional
//    public Poster savePoster(MultipartFile file) throws IOException {
//        Poster poster = new Poster();
//        poster.setPoster(file.getBytes());
//        return fileRepository.save(poster);
//    }

//    @Transactional
//    public Long savePoster(MultipartFile file) {
//        try {
//            Poster poster = new Poster();
//            poster.setPoster(file.getBytes());
//            poster.setTitle(file.getOriginalFilename());
//            Poster savedPoster = fileRepository.save(poster);
//            return savedPoster.getId();
//        } catch (IOException e) {
//            throw new FileStorageException("Could not save poster", e);
//        }
//    }
    @Transactional
    public FileResponseDto savePoster(MultipartFile file) {
        try{
        Poster poster = Poster.builder()
                .poster(file.getBytes())
                .title(file.getOriginalFilename())
                .build();
        Poster savedPoster = fileRepository.save(poster);
        return MAPPER.posterToResponseDtoForCreate(savedPoster);
        } catch (IOException e) {
            throw new FileStorageException("Could not save poster", e);
        }
    }

//    @Transactional
//    public void updatePoster(Long id, MultipartFile file) {
//        if (!fileRepository.existsById(id)) {
//            throw new NotFoundException("Poster with ID " + id + " not found");
//        }
//        try {
//            Poster poster = fileRepository.findById(id).orElseThrow();
//            poster.setPoster(file.getBytes());
//            poster.setTitle(file.getOriginalFilename());
//            fileRepository.save(poster);
//        } catch (IOException e) {
//            throw new FileStorageException("Could not update poster", e);
//        }
//    }

    @Transactional
    public FileResponseDto updatePoster(Long id, MultipartFile file) {
        if (!fileRepository.existsById(id)) {
            throw new NotFoundException("Poster with ID " + id + " not found");
        }
        try{
            Poster poster = fileRepository.findById(id).orElseThrow();
            poster.setPoster(file.getBytes());
            poster.setTitle(file.getOriginalFilename());
            Poster updatedPoster = fileRepository.save(poster);
            return MAPPER.posterToResponseDto(updatedPoster);
        }catch (IOException e) {
            throw new FileStorageException("Could not update poster", e);
        }
    }

    @Transactional
    public FileResponseDto getPoster(Long id) {
        if (!fileRepository.existsById(id)) {
            throw new NotFoundException("Poster with ID " + id + " not found");
        }
        Poster poster = fileRepository.findById(id).orElseThrow();
        return MAPPER.posterToResponseDto(poster);
    }

    @Transactional
    public Poster getPosterAsPoster(Long id) {
        if (!fileRepository.existsById(id)) {
            throw new NotFoundException("Poster with ID " + id + " not found");
        }
        return fileRepository.findById(id).orElseThrow();
    }



//    @Transactional
//    public Poster getPoster(Long id) {
//        if (!fileRepository.existsById(id)) {
//            throw new NotFoundException("Poster with ID " + id + " not found");
//        }
//        return fileRepository.findById(id).orElseThrow();
//    }

    public boolean existsById(Long posterId) {
        return fileRepository.existsById(posterId);
    }
}
