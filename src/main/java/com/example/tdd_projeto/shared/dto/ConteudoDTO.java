package com.example.tdd_projeto.shared.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConteudoDTO {
    private Long id;
    private Long autorId;
    private String autorNome;
    
    @NotBlank
    @Size(min = 5, max = 200)
    private String titulo;
    
    @NotBlank
    private String texto;
    
    private String tipo; // ARTIGO, PERGUNTA, etc
    private LocalDateTime dataPublicacao;
    private Integer visualizacoes;
}