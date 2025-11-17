package com.example.tdd_projeto.shared.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para ResourceNotFoundException")
class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com recurso e ID")
    void deveCriarComRecursoEId() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Usuario", 123L);
        assertEquals("Usuario com ID 123 não encontrado", ex.getMessage());
        assertEquals("RESOURCE_NOT_FOUND", ex.getCode());
    }

    @Test
    @DisplayName("Deve criar exceção com mensagem customizada")
    void deveCriarComMensagemCustomizada() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Recurso não existe");
        assertEquals("Recurso não existe", ex.getMessage());
        assertEquals("RESOURCE_NOT_FOUND", ex.getCode());
        assertTrue(ex instanceof BusinessException);
    }

    @Test
    @DisplayName("Deve formatar diferentes recursos")
    void deveFormatarDiferentesRecursos() {
        ResourceNotFoundException ex1 = new ResourceNotFoundException("Conteudo", 456L);
        ResourceNotFoundException ex2 = new ResourceNotFoundException("Ranking", 789L);
        
        assertEquals("Conteudo com ID 456 não encontrado", ex1.getMessage());
        assertEquals("Ranking com ID 789 não encontrado", ex2.getMessage());
    }
}