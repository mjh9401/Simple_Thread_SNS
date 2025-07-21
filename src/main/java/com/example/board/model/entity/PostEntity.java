package com.example.board.model.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "post", indexes = {@Index(name= "post_userid_idx", columnList = "userid")})
@SQLDelete(sql = "UPDATE \"post\" SET deletedatetime = CURRENT_TIMESTAMP WHERE postid = ?")
// Deprecated in Hibernate 6.3
// @Where(clause = "deletedDateTime IS NULL")
@SQLRestriction("deletedatetime IS NULL")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    
    @Column(columnDefinition = "TEXT")
    private String body;
    
    @Column
    private Long repliesCount = 0L;

    @Column
    private Long likesCount = 0L;
    @Column
    private ZonedDateTime createdDateTime;
    
    @Column
    private ZonedDateTime updateDateTime;
    
    @Column
    private ZonedDateTime deleteDateTime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;

    public Long getPostId() {
        return postId;
    }

    public String getBody() {
        return body;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public ZonedDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public ZonedDateTime getDeleteDateTime() {
        return deleteDateTime;
    }

     public UserEntity getUser() {
        return user;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public void setDeleteDateTime(ZonedDateTime deleteDateTime) {
        this.deleteDateTime = deleteDateTime;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(Long repliesCount) {
        this.repliesCount = repliesCount;
    }
    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    @PrePersist
    private void prePersist(){
        this.createdDateTime = ZonedDateTime.now();
        this.updateDateTime = this.createdDateTime;
    }

    @PreUpdate
    private void preUpdate(){
        this.updateDateTime = ZonedDateTime.now();
    }

    public static PostEntity of(String body, UserEntity user){
        var post = new PostEntity();
        post.setBody(body);
        post.setUser(user);
        
        return post;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((postId == null) ? 0 : postId.hashCode());
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((repliesCount == null) ? 0 : repliesCount.hashCode());
        result = prime * result + ((likesCount == null) ? 0 : likesCount.hashCode());
        result = prime * result + ((createdDateTime == null) ? 0 : createdDateTime.hashCode());
        result = prime * result + ((updateDateTime == null) ? 0 : updateDateTime.hashCode());
        result = prime * result + ((deleteDateTime == null) ? 0 : deleteDateTime.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        PostEntity other = (PostEntity) obj;
        if (postId == null) {
            if (other.postId != null)
                return false;
        } else if (!postId.equals(other.postId))
            return false;
        if (body == null) {
            if (other.body != null)
                return false;
        } else if (!body.equals(other.body))
            return false;
        if (repliesCount == null) {
            if (other.repliesCount != null)
                return false;
        } else if (!repliesCount.equals(other.repliesCount))
            return false;
        if (likesCount == null) {
            if (other.likesCount != null)
                return false;
        } else if (!likesCount.equals(other.likesCount))
            return false;
        if (createdDateTime == null) {
            if (other.createdDateTime != null)
                return false;
        } else if (!createdDateTime.equals(other.createdDateTime))
            return false;
        if (updateDateTime == null) {
            if (other.updateDateTime != null)
                return false;
        } else if (!updateDateTime.equals(other.updateDateTime))
            return false;
        if (deleteDateTime == null) {
            if (other.deleteDateTime != null)
                return false;
        } else if (!deleteDateTime.equals(other.deleteDateTime))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
    
}
