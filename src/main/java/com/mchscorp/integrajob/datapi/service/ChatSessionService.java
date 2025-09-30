package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.ChatSession;
import com.mchscorp.integrajob.datapi.repository.ChatSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;

    public ChatSessionService(ChatSessionRepository chatSessionRepository) {
        this.chatSessionRepository = chatSessionRepository;
    }

    public List<ChatSession> findAll() {
        return chatSessionRepository.findAll();
    }

    public Optional<ChatSession> findById(Integer id) {
        return chatSessionRepository.findById(id);
    }

    public ChatSession save(ChatSession session) {
        return chatSessionRepository.save(session);
    }

    public void delete(Integer id) {
        chatSessionRepository.deleteById(id);
    }
}
