package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    private final UserServiceV2 userServiceV2;


    public UserController(UserServiceV2 userServiceV2) {
        this.userServiceV2 = userServiceV2;
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userServiceV2.getUsers();
    }

    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) {
        userServiceV2.saveUser(request);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
        userServiceV2.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userServiceV2.deleteUser(name);
    }






}
