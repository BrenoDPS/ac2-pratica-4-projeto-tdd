package com.example.tdd_projeto.shared.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para UsuarioDTO")
class UsuarioDTOTest {

    @Test
    @DisplayName("Deve criar UsuarioDTO com builder")
    void deveCriarComBuilder() {
        LocalDateTime now = LocalDateTime.now();
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@example.com")
                .pontuacaoTotal(100)
                .dataCadastro(now)
                .ativo(true)
                .build();
        
        assertEquals(1L, dto.getId());
        assertEquals("João Silva", dto.getNome());
        assertEquals("joao@example.com", dto.getEmail());
        assertEquals(100, dto.getPontuacaoTotal());
        assertTrue(dto.getAtivo());
    }

    @Test
    @DisplayName("Deve criar com construtores")
    void deveCriarComConstrutores() {
        UsuarioDTO vazio = new UsuarioDTO();
        assertNotNull(vazio);
        
        UsuarioDTO completo = new UsuarioDTO(
            1L, "Maria", "maria@test.com", 50, LocalDateTime.now(), true
        );
        assertEquals("Maria", completo.getNome());
    }

    @Test
    @DisplayName("Deve testar getters e setters")
    void deveTestarGettersSetters() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(2L);
        dto.setNome("Ana");
        dto.setEmail("ana@test.com");
        dto.setPontuacaoTotal(200);
        dto.setAtivo(false);
        
        assertEquals(2L, dto.getId());
        assertEquals("Ana", dto.getNome());
        assertFalse(dto.getAtivo());
    }
}