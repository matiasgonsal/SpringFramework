package com.photoapp.photoappusers.ui.controllers;

import com.photoapp.photoappusers.service.UsersService;
import com.photoapp.photoappusers.shared.UserDto;
import com.photoapp.photoappusers.ui.model.CreateUserRequestModel;
import com.photoapp.photoappusers.ui.model.CreateUserResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping("/status/check")
    public String healthCheck (@RequestHeader Map<String, String> headers){
        return "This instance of the PhotoAppUsers Microservice is working properly. Instance: " + headers.get("host");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> createUser (@Valid @RequestBody CreateUserRequestModel createUserRequest){

        //Request Mapping
        UserDto userDto = new UserDto();
        userDto.setFirstName(createUserRequest.getFirstName());
        userDto.setLastName(createUserRequest.getLastName());
        userDto.setEmail(createUserRequest.getEmail());
        userDto.setPassword(createUserRequest.getPassword());

        usersService.createUser(userDto);

        //Response Mapping
        CreateUserResponseModel createUserResponseModel = new CreateUserResponseModel();
        createUserResponseModel.setFirstName(userDto.getFirstName());
        createUserResponseModel.setLastName(userDto.getLastName());
        createUserResponseModel.setEmail(userDto.getEmail());
        createUserResponseModel.setUserId(userDto.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);

    }




}
