package com.photoapp.photoappusers.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestModel {

    @NotNull(message="First Name Can not be null")
    @Size(min = 2, message = "First Name Can not be less than two characters")
    private String firstName;

    @NotNull(message="Last Name Can not be null")
    @Size(min = 2, message = "Last Name Can not be less than two characters")
    private String lastName;

    @NotNull(message="Password Can not be null")
    @Size(min = 8, max = 16, message = "The password must be equal or grater than 8 characters and less than 16 characters")
    private String password;

    @NotNull(message="Email Can not be null")
    @Email
    private String email;
}
