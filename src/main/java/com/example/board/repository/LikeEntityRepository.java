package com.example.board.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.entity.LikeEntity;
import com.example.board.model.entity.PostEntity;
import com.example.board.model.entity.UserEntity;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByUser(UserEntity user);
    List<LikeEntity> findByPost(PostEntity post);
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);
}
