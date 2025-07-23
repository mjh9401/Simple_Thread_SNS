package com.example.board.model.post;

import java.time.ZonedDateTime;

import com.example.board.model.entity.PostEntity;
import com.example.board.model.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Post(Long postId, String body, Long repliesCount, Long likesCount, User user, ZonedDateTime createdDateTime, 
                    ZonedDateTime updateDateTime, ZonedDateTime deleteDateTime, Boolean isLiking) {

    public static Post from(PostEntity postEntity){
        return new Post(
            postEntity.getPostId(),
            postEntity.getBody(),
            postEntity.getRepliesCount(),
            postEntity.getLikesCount(),
            User.from(postEntity.getUser()),
            postEntity.getCreatedDateTime(),
            postEntity.getUpdateDateTime(),
            postEntity.getDeleteDateTime(),
            null
        );
    }

    public static Post from(PostEntity postEntity, boolean isLiking){
        return new Post(
            postEntity.getPostId(),
            postEntity.getBody(),
            postEntity.getRepliesCount(),
            postEntity.getLikesCount(),
            User.from(postEntity.getUser()),
            postEntity.getCreatedDateTime(),
            postEntity.getUpdateDateTime(),
            postEntity.getDeleteDateTime(),
            isLiking
        );
    }
}
