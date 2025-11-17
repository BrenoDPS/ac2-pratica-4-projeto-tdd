package com.example.tdd_projeto.shared.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para RankingDTO")
class RankingDTOTest {

    @Test
    @DisplayName("Deve criar RankingDTO com builder")
    void deveCriarComBuilder() {
        RankingDTO dto = RankingDTO.builder()
                .id(1L)
                .usuarioId(10L)
                .usuarioNome("João")
                .posicao(1)
                .pontuacao(500)
                .variacaoFormatada("+2")
                .periodo("SEMANAL")
                .dataReferencia(LocalDate.now())
                .build();
        
        assertEquals(1L, dto.getId());
        assertEquals(10L, dto.getUsuarioId());
        assertEquals("João", dto.getUsuarioNome());
        assertEquals(1, dto.getPosicao());
        assertEquals("+2", dto.getVariacaoFormatada());
    }

    @Test
    @DisplayName("Deve criar com construtores")
    void deveCriarComConstrutores() {
        RankingDTO vazio = new RankingDTO();
        assertNotNull(vazio);
        
        RankingDTO completo = new RankingDTO(
            1L, 5L, "Maria", 2, 300, "-1", "MENSAL", LocalDate.now()
        );
        assertEquals("Maria", completo.getUsuarioNome());
        assertEquals(2, completo.getPosicao());
    }

    @Test
    @DisplayName("Deve testar getters e setters")
    void deveTestarGettersSetters() {
        RankingDTO dto = new RankingDTO();
        dto.setId(3L);
        dto.setPosicao(5);
        dto.setPontuacao(150);
        dto.setVariacaoFormatada("=");
        
        assertEquals(3L, dto.getId());
        assertEquals(5, dto.getPosicao());
        assertEquals("=", dto.getVariacaoFormatada());
    }
}