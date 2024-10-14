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
@Transactional
@Service
@Validated
public class FileService {
  private final FileRepository fileRepository;
  private static final FileMapper MAPPER = FileMapper.INSTANCE;

  public FileResponseDto savePoster(MultipartFile file) {
    try {
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

  public FileResponseDto updatePoster(Long id, MultipartFile file) {
    try {
      Poster poster = fileRepository.findById(id).orElseThrow(() -> new NotFoundException("Poster with ID " + id + " not found"));
      poster.setPoster(file.getBytes());
      poster.setTitle(file.getOriginalFilename());
      Poster updatedPoster = fileRepository.save(poster);
      return MAPPER.posterToResponseDto(updatedPoster);
    } catch (IOException e) {
      throw new FileStorageException("Could not update poster", e);
    }
  }

  public FileResponseDto getPoster(Long id) {
    return fileRepository.findById(id)
      .map(MAPPER::posterToResponseDto)
      .orElseThrow(() -> new NotFoundException("Poster with ID " + id + " not found"));
  }

  public Poster getPosterAsPoster(Long id) {
    return fileRepository.findById(id).orElseThrow(() -> new NotFoundException("Poster with ID " + id + " not found"));
  }

  public boolean existsById(Long posterId) {
    return fileRepository.existsById(posterId);
  }
}
