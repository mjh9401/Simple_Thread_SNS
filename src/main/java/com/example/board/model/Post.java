package com.example.board.model;

import java.time.ZonedDateTime;

import com.example.board.model.entity.PostEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Post(Long postId, String body, ZonedDateTime createdDateTime, ZonedDateTime updateDateTime, ZonedDateTime deleteDateTime) {

    public static Post from(PostEntity postEntity){
        return new Post(
            postEntity.getPostId(),
            postEntity.getBody(),
            postEntity.getCreatedDateTime(),
            postEntity.getUpdateDateTime(),
            postEntity.getDeleteDateTime()
        );
    }
}
