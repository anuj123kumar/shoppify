package com.anuj.shoppify.auth.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}
