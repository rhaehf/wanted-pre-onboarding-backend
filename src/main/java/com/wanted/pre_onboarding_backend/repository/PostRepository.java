package com.wanted.pre_onboarding_backend.repository;

import com.wanted.pre_onboarding_backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPositionContaining(String keyword);
}
