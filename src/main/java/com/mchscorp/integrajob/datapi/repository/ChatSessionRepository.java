package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Integer> {
    // List<ChatSession> findByUserId(Integer userId);
}
