package com.example.demo.dto;
import com.example.demo.entity.User;

public record UserResponse(long id, String name, String email){
    public static UserResponse from(User user){
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
