package com.example.tdd_projeto.shared.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para ErrorResponse DTO")
class ErrorResponseTest {

    @Test
    @DisplayName("Deve criar ErrorResponse com construtor simples")
    void deveCriarComConstrutorSimples() {
        ErrorResponse error = new ErrorResponse(404, "Not Found", "Recurso não existe", "/api/test");
        
        assertNotNull(error);
        assertEquals(404, error.getStatus());
        assertEquals("Not Found", error.getError());
        assertEquals("Recurso não existe", error.getMessage());
        assertEquals("/api/test", error.getPath());
        assertNotNull(error.getTimestamp());
    }

    @Test
    @DisplayName("Deve criar ErrorResponse com builder")
    void deveCriarComBuilder() {
        LocalDateTime now = LocalDateTime.now();
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(now)
                .status(400)
                .error("Bad Request")
                .message("Dados inválidos")
                .path("/api/usuarios")
                .details(Arrays.asList("Nome obrigatório", "Email inválido"))
                .build();
        
        assertEquals(400, error.getStatus());
        assertEquals(2, error.getDetails().size());
        assertTrue(error.getDetails().contains("Nome obrigatório"));
    }

    @Test
    @DisplayName("Deve testar getters e setters")
    void deveTestarGettersSetters() {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(500);
        error.setError("Internal Error");
        error.setMessage("Erro interno");
        error.setPath("/api/error");
        
        assertEquals(500, error.getStatus());
        assertEquals("Internal Error", error.getError());
    }

    @Test
    @DisplayName("Deve criar com construtor completo")
    void deveCriarComConstrutorCompleto() {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 403, "Forbidden", "Acesso negado", "/api/admin", null
        );
        assertEquals(403, error.getStatus());
        assertNull(error.getDetails());
    }
}