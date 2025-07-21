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
@Table(name = "\"like\"", indexes = {@Index(name= "like_userid_postid_idx", columnList = "userid,postid", unique = true)})
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    
    @Column
    private ZonedDateTime createdDateTime;
    
    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "postid")
    private PostEntity post;


    @PrePersist
    private void prePersist(){
        this.createdDateTime = ZonedDateTime.now();
    }


    public static LikeEntity of(UserEntity user, PostEntity post){
        var like = new LikeEntity();
        like.setUser(user);
        like.setPost(post);

        return like;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((likeId == null) ? 0 : likeId.hashCode());
        result = prime * result + ((createdDateTime == null) ? 0 : createdDateTime.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((post == null) ? 0 : post.hashCode());
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
        LikeEntity other = (LikeEntity) obj;
        if (likeId == null) {
            if (other.likeId != null)
                return false;
        } else if (!likeId.equals(other.likeId))
            return false;
        if (createdDateTime == null) {
            if (other.createdDateTime != null)
                return false;
        } else if (!createdDateTime.equals(other.createdDateTime))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (post == null) {
            if (other.post != null)
                return false;
        } else if (!post.equals(other.post))
            return false;
        return true;
    }


    

 

    

   


    
}
