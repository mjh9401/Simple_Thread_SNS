package com.example.board.model.user;

import java.time.ZonedDateTime;

import com.example.board.model.entity.UserEntity;

public record User(Long userId, String username, String profile, 
                    String description, ZonedDateTime createdDatetime, ZonedDateTime updatedDatetime) {

    public static User from(UserEntity userEntity){
        return new User(
            userEntity.getUserId(),
            userEntity.getUsername(),
            userEntity.getProfile(),
            userEntity.getDescription(),
            userEntity.getCreatedDatetime(),
            userEntity.getUpdatedDatetime()
        );
    }

}
