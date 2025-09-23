package com.example.tdd_projeto.service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraServiceTest {
    @Test
    void deveSomarDoisNumeros() {
        // Arrange
        int a = 2;
        int b = 3;

        // Act
        // Aqui chamaremos um método que ainda NÃO existe
        CalculadoraService calculadora = new CalculadoraService();
        int resultadoSoma = calculadora.somar(a, b);
        int resultadoSubtracao = calculadora.subtrair(a, b);

        // Assert
        assertEquals(5, resultadoSoma); // esperado: 5
        assertEquals(-1, resultadoSubtracao);
    }
}

