package com.example.tdd_projeto.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngajamentoDTO {
    private Long id;
    private Long usuarioId;
    private Long conteudoId;
    private String tipo; // POSTAGEM, RESPOSTA, etc
    private Integer pontos;
    private LocalDateTime dataEngajamento;
}