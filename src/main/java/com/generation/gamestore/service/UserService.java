package com.generation.gamestore.service;


import com.generation.gamestore.model.User;
import com.generation.gamestore.model.UserLogin;
import com.generation.gamestore.repository.UserRepository;
import com.generation.gamestore.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<User> cadastrarUser(User user) {
        if (userRepository.findByUser(user.getUser()).isPresent()) return Optional.empty();

        user.setPassword(encryptPassword(user.getPassword()));

        return Optional.of(userRepository.save(user));
    }

    public Optional<User> atualizarUser(User user) {

        if (userRepository.findById(user.getId()).isPresent()) {

            Optional<User> buscaUser = userRepository.findByUser(user.getUser());

            if ((buscaUser.isPresent()) && (buscaUser.get().getId() != user.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            user.setPassword(encryptPassword(user.getPassword()));

            return Optional.ofNullable(userRepository.save(user));

        }

        return Optional.empty();
    }

    public Optional<UserLogin> authenticationUser(Optional<UserLogin> userLogin) {

        var credenciais = new UsernamePasswordAuthenticationToken(userLogin.get().getUser(), userLogin.get().getPassword());

        Authentication authentication = authenticationManager.authenticate(credenciais);

        if (authentication.isAuthenticated()) {

            Optional<User> user = userRepository.findByUser(userLogin.get().getUser());

            if (user.isPresent()) {

                userLogin.get().setId(user.get().getId());
                userLogin.get().setName(user.get().getName());
                userLogin.get().setImage(user.get().getImage());
                userLogin.get().setToken(generateToken(userLogin.get().getUser()));
                userLogin.get().setPassword(user.get().getPassword());

                return userLogin;
            }
        }

        return Optional.empty();

    }

    private String encryptPassword(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);
    }

    private String generateToken(String user) {
        return "Bearer " + jwtService.generateToken(user);
    }


}
