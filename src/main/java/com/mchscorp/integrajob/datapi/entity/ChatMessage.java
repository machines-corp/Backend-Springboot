package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMessage;

    @ManyToOne
    @JoinColumn(name = "id_session", nullable = false)
    private ChatSession session;

    @Enumerated(EnumType.STRING)
    private Remitente remitente; // USER o BOT

    private String texto;
    private Timestamp fecha;

    public enum Remitente {
        USER, BOT
    }

	public void setIdMessage(Integer id) {
		// TODO Auto-generated method stub
		
	}

    // getters y setters
}
