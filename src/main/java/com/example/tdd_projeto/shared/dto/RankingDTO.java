package com.example.tdd_projeto.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO {
    private Long id;
    private Long usuarioId;
    private String usuarioNome;
    private Integer posicao;
    private Integer pontuacao;
    private String variacaoFormatada; // "+2", "-1", "="
    private String periodo; // DIARIO, SEMANAL, etc
    private LocalDate dataReferencia;
}