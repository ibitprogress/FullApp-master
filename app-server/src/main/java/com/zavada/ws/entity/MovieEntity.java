package com.zavada.ws.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String duration;

    private String releaseYear;

    private Integer rating;

    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
