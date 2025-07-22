package com.example.board.model.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"follow\"", indexes = {@Index(name= "follow_follower_following_idx", columnList = "follower,following", unique = true)})
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;
    
    @Column
    private ZonedDateTime createdDateTime;
    
    @ManyToOne
    @JoinColumn(name = "follower")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "following")
    private UserEntity following;


    @PrePersist
    private void prePersist(){
        this.createdDateTime = ZonedDateTime.now();
    }


    public static FollowEntity of(UserEntity follower, UserEntity following){
        var follow = new FollowEntity();
        follow.setFollower(follower);
        follow.setFollowing(following);

        return follow;
    }


    public Long getFollowId() {
        return followId;
    }


    public void setFollowId(Long followId) {
        this.followId = followId;
    }


    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }


    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }


    public UserEntity getFollower() {
        return follower;
    }


    public void setFollower(UserEntity follower) {
        this.follower = follower;
    }


    public UserEntity getFollowing() {
        return following;
    }


    public void setFollowing(UserEntity following) {
        this.following = following;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((followId == null) ? 0 : followId.hashCode());
        result = prime * result + ((createdDateTime == null) ? 0 : createdDateTime.hashCode());
        result = prime * result + ((follower == null) ? 0 : follower.hashCode());
        result = prime * result + ((following == null) ? 0 : following.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FollowEntity other = (FollowEntity) obj;
        if (followId == null) {
            if (other.followId != null)
                return false;
        } else if (!followId.equals(other.followId))
            return false;
        if (createdDateTime == null) {
            if (other.createdDateTime != null)
                return false;
        } else if (!createdDateTime.equals(other.createdDateTime))
            return false;
        if (follower == null) {
            if (other.follower != null)
                return false;
        } else if (!follower.equals(other.follower))
            return false;
        if (following == null) {
            if (other.following != null)
                return false;
        } else if (!following.equals(other.following))
            return false;
        return true;
    }

    
}
