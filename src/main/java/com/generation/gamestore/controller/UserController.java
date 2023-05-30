package com.generation.gamestore.controller;

import com.generation.gamestore.model.User;
import com.generation.gamestore.model.UserLogin;
import com.generation.gamestore.repository.UserRepository;
import com.generation.gamestore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll(){

        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){

        return userRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{user}")
    public ResponseEntity <Optional<User>> getByUser(@PathVariable String user){
        return ResponseEntity.ok(userRepository.findByUser(user));
    }

    @PostMapping("/login")
    public  ResponseEntity<UserLogin> autenticarUser(@RequestBody Optional<UserLogin> userLogin){
        return userService.authenticationUser(userLogin)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @PostMapping("/register")
    public ResponseEntity<User> postUser(@Valid @RequestBody User user) {

        return userService.cadastrarUser(user)
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @PutMapping("/update")
    public ResponseEntity<User> putUser(@Valid @RequestBody User user) {

        return userService.atualizarUser(user)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

}
