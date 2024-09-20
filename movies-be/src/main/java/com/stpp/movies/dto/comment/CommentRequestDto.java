package com.stpp.movies.dto.comment;

import com.stpp.movies.entities.Discussion;
import com.stpp.movies.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class CommentRequestDto {
    @NotBlank(message = "Content should not be blank")
    @NotNull(message = "Content should not be null")
    @Size(min = 1, max = 1024, message = "Content must be between {min} and {max} characters")
    private String content;

    @NotBlank(message = "Discussion id should not be blank")
    @NotNull(message = "Discussion id should not be null")
    private Long discussion_id;

    @NotBlank(message = "User id should not be blank")
    @NotNull(message = "User id should not be null")
    private Long user_id;
}
