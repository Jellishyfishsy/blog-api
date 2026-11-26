package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "tên ko được để trống")
        @Size(max=100, message = "tối đã 100 kí tự")
        String name,
        @NotBlank(message = "email ko được để trống") 
        @Email(message = "email không đúng định dạng")
        String email 
){}

