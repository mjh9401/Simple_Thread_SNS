package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.board.model.entity.PostEntity;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {}
