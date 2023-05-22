package com.generation.gamestore.repository;

import com.generation.gamestore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre,Long> {

    public List<Genre> findAllByGenreContainingIgnoreCase(
            @Param("genre") String genre);
}
