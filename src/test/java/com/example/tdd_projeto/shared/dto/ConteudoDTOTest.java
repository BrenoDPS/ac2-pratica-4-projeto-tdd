package com.example.tdd_projeto.shared.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para ConteudoDTO")
class ConteudoDTOTest {

    @Test
    @DisplayName("Deve criar ConteudoDTO com builder")
    void deveCriarComBuilder() {
        LocalDateTime now = LocalDateTime.now();
        ConteudoDTO dto = ConteudoDTO.builder()
                .id(1L)
                .autorId(10L)
                .autorNome("João")
                .titulo("Tutorial Spring Boot")
                .texto("Conteúdo do tutorial")
                .tipo("TUTORIAL")
                .dataPublicacao(now)
                .visualizacoes(50)
                .build();
        
        assertEquals(1L, dto.getId());
        assertEquals("Tutorial Spring Boot", dto.getTitulo());
        assertEquals(50, dto.getVisualizacoes());
    }

    @Test
    @DisplayName("Deve criar com construtores")
    void deveCriarComConstrutores() {
        ConteudoDTO vazio = new ConteudoDTO();
        assertNotNull(vazio);
        
        ConteudoDTO completo = new ConteudoDTO(
            1L, 5L, "Maria", "Título", "Texto", "ARTIGO", LocalDateTime.now(), 10
        );
        assertEquals("Título", completo.getTitulo());
    }

    @Test
    @DisplayName("Deve testar getters e setters")
    void deveTestarGettersSetters() {
        ConteudoDTO dto = new ConteudoDTO();
        dto.setId(2L);
        dto.setTitulo("Novo Título");
        dto.setVisualizacoes(100);
        
        assertEquals(2L, dto.getId());
        assertEquals(100, dto.getVisualizacoes());
    }
}