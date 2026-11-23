package com.example.demo.entity;

import jakarta.persistence.*;

@Entity 
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Long getId(){
     return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    protected User(){

    }
}
