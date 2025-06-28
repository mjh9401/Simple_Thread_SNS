package com.example.board.model;

import java.time.ZonedDateTime;


public class Post {
    private Long postId;
    private String body;
    private ZonedDateTime createdDateTime;

    public Post(Long postId, String body, ZonedDateTime createdDateTime) {
        this.postId = postId;
        this.body = body;
        this.createdDateTime = createdDateTime;
    }
    public Long getPostId() {
        return postId;
    }
    public String getBody() {
        return body;
    }
    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((postId == null) ? 0 : postId.hashCode());
        result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((createdDateTime == null) ? 0 : createdDateTime.hashCode());
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
        Post other = (Post) obj;
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
        if (createdDateTime == null) {
            if (other.createdDateTime != null)
                return false;
        } else if (!createdDateTime.equals(other.createdDateTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Post [postId=" + postId + ", body=" + body + ", createdDateTime=" + createdDateTime + "]";
    }

    
    
}
