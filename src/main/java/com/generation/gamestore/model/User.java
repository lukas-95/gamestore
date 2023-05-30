package com.generation.gamestore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "o atributo NOME é obrigatorio")
    private String name;

    @NotNull(message = "o atributo USUARIO é obrigatorio")
    @Email(message = "o atributo usuario vai receber um e-mail válido")
    private String user;

    @NotBlank(message = "o atributo SENHA é obrigatorio")
    @Size(min = 8, message = "A senha tem que ter no mínimo 8 caracteres")
    private String password;

    @Size(max = 5000, message = "O link da foto não pode ser maior de 5.000 caracteres")
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
