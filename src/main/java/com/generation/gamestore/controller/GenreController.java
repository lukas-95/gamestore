package com.generation.gamestore.controller;

import com.generation.gamestore.model.Genre;
import com.generation.gamestore.model.Genre;
import com.generation.gamestore.repository.GenreRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genre")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<Genre>> getAll() {
        return ResponseEntity.ok(genreRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getById(@PathVariable Long id){
        return genreRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @GetMapping("/genre/{genre}")
    public  ResponseEntity<List<Genre>> getByGenre(@PathVariable String genre){
        return ResponseEntity.ok(genreRepository.findAllByGenreContainingIgnoreCase(genre));
    }

    @PostMapping
    public ResponseEntity<Genre> post(@Valid @RequestBody Genre genre){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genreRepository.save(genre));
    }

    @PutMapping
    public ResponseEntity<Genre> put(@Valid @RequestBody Genre genre){
        return genreRepository.findById(genre.getId())
                .map(response -> ResponseEntity.status(HttpStatus.OK)
                        .body(genreRepository.save(genre)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Genre> genre = genreRepository.findById(id);

        if (genre.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        genreRepository.deleteById(id);
    }


}
