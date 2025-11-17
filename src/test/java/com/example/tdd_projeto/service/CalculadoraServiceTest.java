package com.example.tdd_projeto.service;

import com.example.tdd_projeto.application.service.CalculadoraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para CalculadoraService")
public class CalculadoraServiceTest {
    
    private CalculadoraService calculadora;
    
    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraService();
    }
    
    @Test
    @DisplayName("Deve somar dois números")
    void deveSomarDoisNumeros() {
        assertEquals(5, calculadora.somar(2, 3));
        assertEquals(0, calculadora.somar(-5, 5));
        assertEquals(-10, calculadora.somar(-5, -5));
    }
    
    @Test
    @DisplayName("Deve subtrair dois números")
    void deveSubtrairDoisNumeros() {
        assertEquals(-1, calculadora.subtrair(2, 3));
        assertEquals(10, calculadora.subtrair(15, 5));
        assertEquals(0, calculadora.subtrair(5, 5));
    }
    
    @Test
    @DisplayName("Deve multiplicar dois números")
    void deveMultiplicarDoisNumeros() {
        assertEquals(6, calculadora.multiplicar(2, 3));
        assertEquals(0, calculadora.multiplicar(0, 5));
        assertEquals(-15, calculadora.multiplicar(-3, 5));
    }
    
    @Test
    @DisplayName("Deve dividir dois números")
    void deveDividirDoisNumeros() {
        assertEquals(2, calculadora.dividir(6, 3));
        assertEquals(0, calculadora.dividir(0, 5));
        assertEquals(-3, calculadora.dividir(-9, 3));
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao dividir por zero")
    void deveLancarExcecaoAoDividirPorZero() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> calculadora.dividir(10, 0)
        );
        assertEquals("Divisão por zero não é permitida", exception.getMessage());
    }
}