package com.zavada.ws.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;

    private String title;

    private String description;

    private String duration;

    private String releaseYear;

    private Integer rating;

    private String image;

    private CategoryDTO category;
}
