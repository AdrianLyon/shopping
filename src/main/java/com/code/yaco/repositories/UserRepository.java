package com.code.yaco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.yaco.models.User;

public interface UserRepository extends JpaRepository<User,Long>{
    User findByUsername(String username);
    User findEntityByUsername(String username) ;
}
