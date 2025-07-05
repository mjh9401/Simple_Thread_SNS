package com.example.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.exception.user.UserAlreadyExistsException;
import com.example.board.exception.user.UserNotFoundException;
import com.example.board.model.entity.UserEntity;
import com.example.board.model.user.User;
import com.example.board.repository.UserEntityRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userEntityRepository.findByUsername(username).orElseThrow(
            ()-> new UserNotFoundException(username)
        );
    }

    public User signUp(String username, String password) {
        userEntityRepository
            .findByUsername(username)
            .ifPresent(
                user ->{
                    throw new UserAlreadyExistsException(); 
                });
        
        var savedUserEntity = userEntityRepository.save(UserEntity.of(username, passwordEncoder.encode(password)));

        return User.from(savedUserEntity);
    }

}
