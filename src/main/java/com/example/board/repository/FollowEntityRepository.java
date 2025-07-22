package com.example.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.entity.FollowEntity;
import com.example.board.model.entity.UserEntity;

@Repository
public interface FollowEntityRepository extends JpaRepository<FollowEntity, Long> {
    List<FollowEntity> findByFollower(UserEntity follower);
    List<FollowEntity> findByFollowing(UserEntity following);
    Optional<FollowEntity> findByFollowerAndFollowing(UserEntity follower, UserEntity following);
}
