package com.example.board.model.entity;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Random;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"user\"")
@SQLDelete(sql = "UPDATE \"user\" SET deletedatetime = CURRENT_TIMESTAMP WHERE userid = ?")
// Deprecated in Hibernate 6.3
// @Where(clause = "deletedDateTime IS NULL")
@SQLRestriction("deletedatetime IS NULL")
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String profile;

    @Column
    private String description;

    @Column
    private ZonedDateTime createdDatetime;

    @Column
    private ZonedDateTime updatedDatetime;

    @Column
    private ZonedDateTime deletedDatetime;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProfile() {
        return profile;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public ZonedDateTime getUpdatedDatetime() {
        return updatedDatetime;
    }

    public ZonedDateTime getDeletedDatetime() {
        return deletedDatetime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedDatetime(ZonedDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public void setUpdatedDatetime(ZonedDateTime updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    public void setDeletedDatetime(ZonedDateTime deletedDatetime) {
        this.deletedDatetime = deletedDatetime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((profile == null) ? 0 : profile.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((createdDatetime == null) ? 0 : createdDatetime.hashCode());
        result = prime * result + ((updatedDatetime == null) ? 0 : updatedDatetime.hashCode());
        result = prime * result + ((deletedDatetime == null) ? 0 : deletedDatetime.hashCode());
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
        UserEntity other = (UserEntity) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (profile == null) {
            if (other.profile != null)
                return false;
        } else if (!profile.equals(other.profile))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (createdDatetime == null) {
            if (other.createdDatetime != null)
                return false;
        } else if (!createdDatetime.equals(other.createdDatetime))
            return false;
        if (updatedDatetime == null) {
            if (other.updatedDatetime != null)
                return false;
        } else if (!updatedDatetime.equals(other.updatedDatetime))
            return false;
        if (deletedDatetime == null) {
            if (other.deletedDatetime != null)
                return false;
        } else if (!deletedDatetime.equals(other.deletedDatetime))
            return false;
        return true;
    }

    public static UserEntity of(String username, String password){
        var userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        // Set random profile image url
        userEntity.setProfile("https://avatar.iran.liara.run/public/" + new Random().nextInt(100));
      
        return userEntity;
    }

    @PrePersist
    private void prePersist(){
        this.createdDatetime = ZonedDateTime.now();
        this.updatedDatetime = this.createdDatetime;
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedDatetime = ZonedDateTime.now();
    }
}
