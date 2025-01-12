package com.code.yaco.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.code.yaco.dto.UserDTO;
import com.code.yaco.models.User;
import com.code.yaco.repositories.UserRepository;

@Service
public class UserService {
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    public UserDTO registerUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        User saveUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(saveUser.getId());
        userDTO.setUsername(saveUser.getUsername());
        return userDTO;
    }

    public UserDTO findByUsername(String username){
        User user = userRepository.findByUsername(username);
        if (user == null)return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
    
    public User findEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
