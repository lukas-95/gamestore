package com.generation.gamestore.controller;

import com.generation.gamestore.model.Product;
import com.generation.gamestore.repository.GenreRepository;
import com.generation.gamestore.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(){

        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return productRepository.findById(id)
                .map(response -> ResponseEntity.ok(response))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/title/{title}")
    public  ResponseEntity<List<Product>> getByTitle(@PathVariable String title){
        return ResponseEntity.ok(productRepository.findAllByTitleContainingIgnoreCase(title));
    }

    @PostMapping
    public ResponseEntity<Product> post(@Valid @RequestBody Product product){
        if (genreRepository.existsById(product.getGenre().getId()))
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(product));
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre doesn't exist!", null);
    }

    @PutMapping
    public ResponseEntity<Product> put(@Valid @RequestBody Product product){
        if (productRepository.existsById(product.getId())){
            if (genreRepository.existsById(product.getGenre().getId()))
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(productRepository.save(product));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Genre doesn't exist!", null);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        productRepository.deleteById(id);
    }








}
