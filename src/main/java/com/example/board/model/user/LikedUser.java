package com.example.board.model.user;

import java.time.ZonedDateTime;

public record LikedUser(Long userId, String username, String profile, 
                    String description,Long followersCount, Long follwingsCount, ZonedDateTime createdDatetime, 
                    ZonedDateTime updatedDatetime, Boolean isFollowing, Long likedPostId, ZonedDateTime likedDateTime) {

    public static LikedUser from(User user, Long likedPostId, ZonedDateTime likedDateTime){
        return new LikedUser(
            user.userId(),
            user.username(),
            user.profile(),
            user.description(),
            user.followersCount(),
            user.follwingsCount(),
            user.createdDatetime(),
            user.updatedDatetime(),
            user.isFollowing(), 
            likedPostId,
            likedDateTime
        );
    }

    

}
