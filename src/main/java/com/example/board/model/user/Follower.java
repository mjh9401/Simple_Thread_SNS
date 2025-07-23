package com.example.board.model.user;

import java.time.ZonedDateTime;

public record Follower(Long userId, String username, String profile, 
                    String description,Long followersCount, Long follwingsCount, ZonedDateTime createdDatetime, 
                    ZonedDateTime updatedDatetime,ZonedDateTime followedDateTime, Boolean isFollowing) {

    public static Follower from(User user, ZonedDateTime follDateTime){
        return new Follower(
            user.userId(),
            user.username(),
            user.profile(),
            user.description(),
            user.followersCount(),
            user.follwingsCount(),
            user.createdDatetime(),
            user.updatedDatetime(),
            follDateTime,
            user.isFollowing()
        );
    }

    

}
