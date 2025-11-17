package com.example.tdd_projeto.domain.entity;

import com.example.tdd_projeto.domain.valueobject.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para Entidade Conteudo")
class ConteudoTest {

    private Usuario autor;
    private Conteudo conteudo;

    @BeforeEach
    void setUp() {
        autor = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email(Email.de("joao@example.com"))
                .build();

        conteudo = Conteudo.builder()
                .id(1L)
                .autor(autor)
                .titulo("Introdução ao Spring Boot")
                .texto("Tutorial sobre Spring Boot")
                .tipo(Conteudo.TipoConteudo.TUTORIAL)
                .visualizacoes(0)
                .build();
    }

    @Test
    @DisplayName("Deve criar conteúdo e testar getters/setters")
    void deveCriarConteudoETestarGettersSetters() {
        assertNotNull(conteudo);
        assertEquals(1L, conteudo.getId());
        assertEquals("Introdução ao Spring Boot", conteudo.getTitulo());
        
        conteudo.setTitulo("Novo Título");
        assertEquals("Novo Título", conteudo.getTitulo());
    }

    @Test
    @DisplayName("Deve criar com construtores vazio e completo")
    void deveCriarComConstrutores() {
        Conteudo vazio = new Conteudo();
        assertNotNull(vazio);
        
        Conteudo completo = new Conteudo(1L, autor, "T", "Texto", 
            Conteudo.TipoConteudo.ARTIGO, LocalDateTime.now(), 5, null);
        assertEquals("T", completo.getTitulo());
    }

    @Test
    @DisplayName("Deve visualizar e incrementar contador")
    void deveVisualizarEIncrementarContador() {
        conteudo.setVisualizacoes(10);
        conteudo.visualizar();
        conteudo.visualizar();
        assertEquals(12, conteudo.getVisualizacoes());
    }

    @Test
    @DisplayName("Deve adicionar engajamentos e retornar total")
    void deveAdicionarEngajamentosERetornarTotal() {
        assertEquals(0, conteudo.getTotalEngajamentos());
        
        Engajamento eng = Engajamento.builder()
                .id(1L)
                .tipo(Engajamento.TipoEngajamento.CURTIDA)
                .build();
        
        conteudo.adicionarEngajamento(eng);
        assertEquals(1, conteudo.getTotalEngajamentos());
        assertEquals(conteudo, eng.getConteudo());
    }

    @Test
    @DisplayName("Deve executar onCreate e inicializar campos")
    void deveExecutarOnCreateEInicializarCampos() {
        Conteudo novo = new Conteudo();
        novo.onCreate();
        assertNotNull(novo.getDataPublicacao());
        assertEquals(0, novo.getVisualizacoes());
        
        LocalDateTime dataFixa = LocalDateTime.of(2024, 1, 1, 10, 0);
        novo.setDataPublicacao(dataFixa);
        novo.onCreate();
        assertEquals(dataFixa, novo.getDataPublicacao());
    }

    @Test
    @DisplayName("Deve testar equals, hashCode e toString")
    void deveTestarEqualsHashCodeToString() {
        Conteudo c1 = new Conteudo();
        c1.setId(1L);
        Conteudo c2 = new Conteudo();
        c2.setId(1L);
        
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertTrue(conteudo.toString().contains("Conteudo"));
    }

    @Test
    @DisplayName("Deve testar enum TipoConteudo")
    void deveTestarEnumTipoConteudo() {
        assertEquals(5, Conteudo.TipoConteudo.values().length);
        assertEquals(Conteudo.TipoConteudo.ARTIGO, Conteudo.TipoConteudo.valueOf("ARTIGO"));
        assertEquals(Conteudo.TipoConteudo.TUTORIAL, Conteudo.TipoConteudo.valueOf("TUTORIAL"));
    }
}