package com.mchscorp.integrajob.datapi.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CHAT_SESSION")
public class ChatSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSession;

    private Integer userId;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;

    @OneToMany(mappedBy = "session")
    private List<ChatMessage> mensajes;

	public void setIdSession(Integer id) {
		// TODO Auto-generated method stub
		
	}

    // getters y setters
}
