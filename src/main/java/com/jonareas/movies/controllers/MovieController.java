package com.jonareas.movies.controllers;

import com.jonareas.movies.dtos.MovieDto;
import com.jonareas.movies.entities.Movie;
import com.jonareas.movies.repositories.MovieRepository;
import com.jonareas.movies.services.MovieServiceImpl;
import com.sipios.springsearch.anotation.SearchSpec;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/movies")
public class MovieController extends BaseControllerImpl<Movie, Long, MovieServiceImpl> {

    private final ModelMapper modelMapper;
    private MovieRepository repository;

    MovieController(MovieServiceImpl service,
                    ModelMapper modelMapper,
                    MovieRepository repository) {
        super(service);
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    //https://github.com/sipios/spring-search
    //https://www.sipios.com/blog-tech/how-to-generate-an-advanced-search-api-spring-boot
   //ejemplos
    //http://localhost:8080/movies/listado/?pageNumber=1&pageSize=2
    //http://localhost:8080/movies/listado?search=(type:'largo metraje')&pageNumber=1&pageSize=2
    //http://localhost:8080/movies/listado?search=(title:%2790%20minutos%20en%20el%20cielo%27)

    // const val BASE_URL = "http://localhost:8080"

    // GET("movies/listado?search={movie_query_string}")


    // (Repository)
    // fun getMovieByQuery @Query(movie_query_string) => "(title : $string)"

    // (ViewModel)
    // fun searchMovieByTitle(String title) = repository.getmoviebyquery(query_string  = "(title : $title)")
    // fun searchMovieByLength() = repository.getmoviebyquery(query_string  = "(length : $variable)")
    // fun searchMovieBySynopsis() = repository.getmoviebyquery(query_string  = "(synopsis : $variable)")


    @GetMapping("/listado")
     public Page<MovieDto> listado(Pageable pageable,
                                   @SearchSpec Specification<MovieDto> specification) {

        Page<Movie> results = getResults(pageable, specification);
        var dto = results.map(item -> modelMapper.map(item, MovieDto.class));

        //dto.getContent().get(0).setNationality("Prueba de Nacionalidad");
        return dto;

    }



    private Page<Movie> getResults(Pageable pageable,
                                   Specification<MovieDto> specs) {

        Specification<Movie> specification =
                specs != null ? modelMapper.map(specs, getTypeEntitySpec()) : null;
        return repository.findAll(specification, pageable);

    }

    private Type getTypeEntitySpec() {
        return new TypeToken<Specification<Movie>>() {}.getType();
    }

}
