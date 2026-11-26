package com.example.demo.service;

import com.example.demo.dto.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.exception.*;

import java.util.List;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User create(UserRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new DuplicateEmailException(request.email());
        }
        return userRepository.save(new User(request.name(),request.email()));
    }

    public User getById(long id){
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user id"));
    }

    public List<User> getAll(){
        List<User> userList = userRepository.findAll(Sort.by("id"));
        if(userList.isEmpty()){
            throw new ResourceNotFoundException("Danh sách trống");
        }
        return userList;
    }

    public User update(Long id, UserRequest request){
        User user = getById(id);
        if(userRepository.existsByEmail(request.email())){
            throw new DuplicateEmailException(request.email());
        }
        user.setName(request.name());
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    public void delete(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Không tìm thấy user id = "+id);
        }
        userRepository.deleteById(id);
    }
}