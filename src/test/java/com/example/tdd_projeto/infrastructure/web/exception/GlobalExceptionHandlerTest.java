package com.example.tdd_projeto.infrastructure.web.exception;

import com.example.tdd_projeto.shared.dto.ErrorResponse;
import com.example.tdd_projeto.shared.exception.BusinessException;
import com.example.tdd_projeto.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        when(request.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    @DisplayName("Deve tratar ResourceNotFoundException")
    void deveTratarResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Usuario", 1L);
        ResponseEntity<ErrorResponse> response = handler.handleResourceNotFound(ex, request);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Recurso não encontrado", response.getBody().getError());
    }

    @Test
    @DisplayName("Deve tratar BusinessException")
    void deveTratarBusinessException() {
        BusinessException ex = new BusinessException("Erro de validação");
        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(ex, request);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Erro de negócio", response.getBody().getError());
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException")
    void deveTratarMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error = new FieldError("usuario", "nome", "Nome obrigatório");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(error));
        
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
            mock(MethodParameter.class), bindingResult);
        
        ResponseEntity<ErrorResponse> response = handler.handleValidationException(ex, request);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro de validação", response.getBody().getError());
        assertTrue(response.getBody().getDetails().contains("Nome obrigatório"));
    }

    @Test
    @DisplayName("Deve tratar Exception genérica")
    void deveTratarExceptionGenerica() {
        Exception ex = new Exception("Erro inesperado");
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex, request);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().getStatus());
    }
}