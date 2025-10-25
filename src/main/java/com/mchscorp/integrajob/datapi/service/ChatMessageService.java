package com.mchscorp.integrajob.datapi.service;

import com.mchscorp.integrajob.datapi.entity.ChatMessage;
import com.mchscorp.integrajob.datapi.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }

    public Optional<ChatMessage> findById(Integer id) {
        return chatMessageRepository.findById(id);
    }

    public List<ChatMessage> findBySession(Integer idSession) {
        return chatMessageRepository.findBySessionIdSession(idSession);
    }

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public void delete(Integer id) {
        chatMessageRepository.deleteById(id);
    }
}
