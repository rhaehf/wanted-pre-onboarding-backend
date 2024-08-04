package com.wanted.pre_onboarding_backend.repository;

import com.wanted.pre_onboarding_backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
