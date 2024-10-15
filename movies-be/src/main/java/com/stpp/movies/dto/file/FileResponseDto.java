package com.stpp.movies.dto.file;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class FileResponseDto {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Id
  private Long id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Lob
  @Nullable
  private byte[] poster;
}
