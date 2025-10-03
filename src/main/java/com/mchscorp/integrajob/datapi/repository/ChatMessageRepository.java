package com.mchscorp.integrajob.datapi.repository;

import com.mchscorp.integrajob.datapi.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    // Extra: listar mensajes por sesión
    List<ChatMessage> findBySessionIdSession(Integer idSession);
}
