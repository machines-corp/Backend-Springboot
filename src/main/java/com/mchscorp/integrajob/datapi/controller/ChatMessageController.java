package com.mchscorp.integrajob.datapi.controller;

import com.mchscorp.integrajob.datapi.entity.ChatMessage;
import com.mchscorp.integrajob.datapi.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public List<ChatMessage> getAll() {
        return chatMessageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatMessage> getById(@PathVariable Integer id) {
        return chatMessageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/session/{idSession}")
    public List<ChatMessage> getBySession(@PathVariable Integer idSession) {
        return chatMessageService.findBySession(idSession);
    }

    @PostMapping
    public ChatMessage create(@RequestBody ChatMessage chatMessage) {
        return chatMessageService.save(chatMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatMessage> update(@PathVariable Integer id, @RequestBody ChatMessage chatMessage) {
        return chatMessageService.findById(id)
                .map(existing -> {
                    chatMessage.setIdMessage(id);
                    return ResponseEntity.ok(chatMessageService.save(chatMessage));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        return chatMessageService.findById(id)
                .map(existing -> {
                    chatMessageService.delete(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
