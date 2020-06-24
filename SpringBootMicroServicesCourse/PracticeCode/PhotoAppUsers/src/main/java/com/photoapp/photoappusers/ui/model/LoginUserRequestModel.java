package com.photoapp.photoappusers.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserRequestModel {

    @NotNull(message="Email Can not be null")
    @Email
    private String email;

    @NotNull(message="Password Can not be null")
    private String password;
}
