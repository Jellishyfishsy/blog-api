package com.example.demo.service;

import com.example.demo.dto.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User create(UserRequest request){
        return userRepository.save(new User(request.name(),request.email()));
    }

    public Optional<User> getById(long id){
        return userRepository.findById(id);
    }

    public List<User> getAll(){
        return userRepository.findAll(Sort.by("id"));
    }

    public Optional<User> update(Long id, UserRequest request){
        return userRepository.findById(id).map(user -> {
            user.setName(request.name());
            user.setEmail(request.email());
            return userRepository.save(user);
        });
    }

    public Boolean delete(Long id){
        if(!userRepository.existsById(id)){
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}