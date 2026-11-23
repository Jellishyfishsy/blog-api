package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.dto.*;
import com.example.demo.service.UserService;

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
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getById(id).map(UserResponse::from).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // POST: tạo người dùng mới, trả về 201
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User newUser = userService.create(request); 
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(newUser));
    }

    // PUT: cập nhật người dùng, trả về 404 nếu không tồn tại
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.update(id, request).map(UserResponse::from).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: xóa người dùng, trả về 204 nếu thành công, 404 nếu không tồn tại
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userService.delete(id)?ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }

 }
