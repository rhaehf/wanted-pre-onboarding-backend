package com.wanted.pre_onboarding_backend.repository;

import com.wanted.pre_onboarding_backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // post에서 position에 해당 keyword가 있는지 찾는 메소드
    List<Post> findByPositionContaining(String keyword);
}
