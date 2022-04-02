package com.jonareas.movies.dtos;

import com.jonareas.movies.entities.Nationality;
import com.jonareas.movies.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDto {

    private Long id;
    private String title;
    private String type;
    private Nationality nationality;
    private Rating rating;
    private String length;
    private String synopsis;
    private String name;


}
