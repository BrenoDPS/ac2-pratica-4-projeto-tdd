package com.example.tdd_projeto.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para classe Usuario do application.service")
class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("João Silva");
    }

    @Test
    @DisplayName("Deve criar usuário com nome")
    void deveCriarUsuarioComNome() {
        assertEquals("João Silva", usuario.getNome());
    }

    @Test
    @DisplayName("Deve registrar engajamento de postagem")
    void deveRegistrarEngajamentoPostagem() {
        usuario.registrarEngajamento("postagem");
        List<String> engajamentos = usuario.getEngajamentos();
        assertEquals(1, engajamentos.size());
        assertTrue(engajamentos.contains("postagem"));
    }

    @Test
    @DisplayName("Deve registrar múltiplos engajamentos do mesmo tipo")
    void deveRegistrarMultiplosEngajamentosMesmoTipo() {
        usuario.registrarEngajamento("postagem");
        usuario.registrarEngajamento("postagem");
        usuario.registrarEngajamento("postagem");
        List<String> engajamentos = usuario.getEngajamentos();
        assertEquals(3, engajamentos.size());
    }

    @Test
    @DisplayName("Deve registrar engajamentos de tipos diferentes")
    void deveRegistrarEngajamentosTiposDiferentes() {
        usuario.registrarEngajamento("postagem");
        usuario.registrarEngajamento("resposta");
        usuario.registrarEngajamento("curtida");
        List<String> engajamentos = usuario.getEngajamentos();
        assertEquals(3, engajamentos.size());
    }

    @Test
    @DisplayName("Deve calcular pontuação para postagens")
    void deveCalcularPontuacaoPostagens() {
        usuario.registrarEngajamento("postagem");
        usuario.registrarEngajamento("postagem");
        assertEquals(100, usuario.getPontuacaoTotal()); // 50 * 2
    }

    @Test
    @DisplayName("Deve calcular pontuação para respostas")
    void deveCalcularPontuacaoRespostas() {
        usuario.registrarEngajamento("resposta");
        usuario.registrarEngajamento("resposta");
        assertEquals(60, usuario.getPontuacaoTotal()); // 30 * 2
    }

    @Test
    @DisplayName("Deve calcular pontuação para outros tipos (default)")
    void deveCalcularPontuacaoOutrosTipos() {
        usuario.registrarEngajamento("curtida");
        usuario.registrarEngajamento("comentario");
        usuario.registrarEngajamento("compartilhamento");
        assertEquals(30, usuario.getPontuacaoTotal()); // 10 * 3
    }

    @Test
    @DisplayName("Deve calcular pontuação mista")
    void deveCalcularPontuacaoMista() {
        usuario.registrarEngajamento("postagem");  // 50
        usuario.registrarEngajamento("resposta");   // 30
        usuario.registrarEngajamento("curtida");    // 10
        assertEquals(90, usuario.getPontuacaoTotal());
    }

    @Test
    @DisplayName("Deve retornar zero quando sem engajamentos")
    void deveRetornarZeroSemEngajamentos() {
        assertEquals(0, usuario.getPontuacaoTotal());
        assertTrue(usuario.getEngajamentos().isEmpty());
    }
}