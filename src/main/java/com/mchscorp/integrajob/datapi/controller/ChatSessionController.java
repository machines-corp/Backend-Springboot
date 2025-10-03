package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.ChatSession;
import com.mchscorp.integrajob.datapi.service.ChatSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/sessions")
public class ChatSessionController {

    private final ChatSessionService chatSessionService;

    public ChatSessionController(ChatSessionService chatSessionService) {
        this.chatSessionService = chatSessionService;
    }

    @GetMapping
    public List<ChatSession> getAll() {
        return chatSessionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatSession> getById(@PathVariable Integer id) {
        return chatSessionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ChatSession create(@RequestBody ChatSession session) {
        return chatSessionService.save(session);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatSession> update(@PathVariable Integer id, @RequestBody ChatSession session) {
        return chatSessionService.findById(id)
                .map(existing -> {
                    session.setIdSession(id);
                    return ResponseEntity.ok(chatSessionService.save(session));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return chatSessionService.findById(id)
                .map(existing -> {
                    chatSessionService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}