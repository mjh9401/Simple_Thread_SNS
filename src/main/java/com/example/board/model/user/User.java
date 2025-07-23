package com.example.board.model.user;

import java.time.ZonedDateTime;

import com.example.board.model.entity.UserEntity;

public record User(Long userId, String username, String profile, 
                    String description,Long followersCount, Long follwingsCount, ZonedDateTime createdDatetime, 
                    ZonedDateTime updatedDatetime, Boolean isFollowing) {

    public static User from(UserEntity userEntity){
        return new User(
            userEntity.getUserId(),
            userEntity.getUsername(),
            userEntity.getProfile(),
            userEntity.getDescription(),
            userEntity.getFollowersCount(),
            userEntity.getFollwingsCount(),
            userEntity.getCreatedDatetime(),
            userEntity.getUpdatedDatetime(),
            null
        );
    }

    public static User from(UserEntity userEntity, boolean isFollowing){
        return new User(
            userEntity.getUserId(),
            userEntity.getUsername(),
            userEntity.getProfile(),
            userEntity.getDescription(),
            userEntity.getFollowersCount(),
            userEntity.getFollwingsCount(),
            userEntity.getCreatedDatetime(),
            userEntity.getUpdatedDatetime(),
            isFollowing
        );
    }

    

}
