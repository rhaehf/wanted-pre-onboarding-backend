package com.wanted.pre_onboarding_backend.repository;

import com.wanted.pre_onboarding_backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // post에서 position에 해당 keyword가 있는지 찾는 메소드
    List<Post> findByPositionContaining(String keyword);

    // JPQL 쿼리로 작성
    @Query("SELECT p.postId FROM Post p WHERE p.company.companyId = :companyId")
    List<Long> findPostIdsByCompanyId(@Param("companyId") Long companyId);
}
