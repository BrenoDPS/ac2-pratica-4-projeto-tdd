package com.example.tdd_projeto.shared.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para BusinessException")
class BusinessExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem simples")
    void deveCriarComMensagemSimples() {
        BusinessException ex = new BusinessException("Erro de negócio");
        assertEquals("Erro de negócio", ex.getMessage());
        assertEquals("BUSINESS_ERROR", ex.getCode());
    }

    @Test
    @DisplayName("Deve criar exceção com código customizado")
    void deveCriarComCodigoCustomizado() {
        BusinessException ex = new BusinessException("CUSTOM_CODE", "Mensagem");
        assertEquals("Mensagem", ex.getMessage());
        assertEquals("CUSTOM_CODE", ex.getCode());
    }

    @Test
    @DisplayName("Deve criar exceção com causa")
    void deveCriarComCausa() {
        Throwable causa = new RuntimeException("Causa");
        BusinessException ex = new BusinessException("Erro", causa);
        assertEquals("Erro", ex.getMessage());
        assertEquals(causa, ex.getCause());
        assertTrue(ex instanceof RuntimeException);
    }
}