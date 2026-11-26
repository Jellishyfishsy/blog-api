package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.dto.*;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
//enum chứa các status code của HTTP
import org.springframework.http.ResponseEntity;
//cho phép tự do tùy biến Mã trạng thái
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Khai báo controller trả về dữ liệu cho client
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    // GET: Lấy danh sách người dùng, sắp xếp theo id tăng dần, trả về 200
    @GetMapping
    // khi client gửi request tới /api/users sẽ tự động gọi hàm này
    public List<UserResponse> getAllUsers() {
        // Vì có RestController nên List sẽ tự động chuyển về mảng dạng JSON
        return userService.getAll().stream().map(UserResponse::from).toList();
    }

    // GET: lấy user theo id, trả về 404 nếu không tồn tại
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return UserResponse.from(userService.getById(id));
    }

    // POST: tạo người dùng mới, trả về 201
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        User newUser = userService.create(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(newUser));
    }

    // PUT: cập nhật người dùng, trả về 404 nếu không tồn tại
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest request) {
        return UserResponse.from(userService.update(id, request));
    }

    // DELETE: xóa người dùng, trả về 204 nếu thành công, 404 nếu không tồn tại
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

 }
