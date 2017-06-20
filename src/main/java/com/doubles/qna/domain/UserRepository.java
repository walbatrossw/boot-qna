package com.doubles.qna.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    // User 타입의 userId로 조회
    User findByUserId(String userId);
}
