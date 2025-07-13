package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.exception.user.UserAlreadyExistsException;
import com.example.board.exception.user.UserNotAllowedException;
import com.example.board.exception.user.UserNotFoundException;
import com.example.board.model.entity.UserEntity;
import com.example.board.model.user.User;
import com.example.board.model.user.UserAuthenticationResponse;
import com.example.board.model.user.UserPatchRequestBody;
import com.example.board.repository.UserEntityRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;


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

    public UserAuthenticationResponse authenticate(String username, String password) {
        var userEntity = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        
        if(passwordEncoder.matches(password, userEntity.getPassword())){
            var accessToken = jwtService.generateAccessToken(userEntity);
            return new UserAuthenticationResponse(accessToken);
        }else{
            throw new UserNotFoundException();
        }
    }

    public List<User> getUsers(String query) {

        List<UserEntity> userEntities;
        
       if(query != null && !query.isBlank()){
            userEntities = userEntityRepository.findByUsernameContaining(query);
        }else{
            userEntities = userEntityRepository.findAll();
        }

        return userEntities.stream().map(User::from).toList();

    }

    public User getUser(String username) {
        var userEntity = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));  

        return User.from(userEntity);
    }

    public User updateUser(String username, UserPatchRequestBody userPatchRequestBody, UserEntity currentUser) {
        var userEntity = userEntityRepository.findByUsername(username)
                        .orElseThrow(()-> new UserNotFoundException(username));
        if(!userEntity.equals(currentUser)){
            throw new UserNotAllowedException();
        }

        if(userPatchRequestBody.description() != null){
            userEntity.setDescription(userPatchRequestBody.description());
        }
        
        return User.from(userEntityRepository.save(userEntity));
    }

}
