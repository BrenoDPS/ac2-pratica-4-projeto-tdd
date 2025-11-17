package com.example.tdd_projeto.domain.entity;

import com.example.tdd_projeto.domain.valueobject.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para Entidade Engajamento")
class EngajamentoTest {

    private Usuario usuario;
    private Engajamento engajamento;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nome("Maria Silva")
                .email(Email.de("maria@example.com"))
                .build();

        engajamento = Engajamento.builder()
                .id(1L)
                .usuario(usuario)
                .tipo(Engajamento.TipoEngajamento.CURTIDA)
                .pontos(10)
                .build();
    }

    @Test
    @DisplayName("Deve criar engajamento e testar getters/setters")
    void deveCriarEngajamentoETestarGettersSetters() {
        assertNotNull(engajamento);
        assertEquals(1L, engajamento.getId());
        assertEquals(10, engajamento.getPontos());
        
        engajamento.setPontos(100);
        assertEquals(100, engajamento.getPontos());
    }

    @Test
    @DisplayName("Deve criar com construtores vazio e completo")
    void deveCriarComConstrutores() {
        Engajamento vazio = new Engajamento();
        assertNotNull(vazio);
        
        Engajamento completo = new Engajamento(1L, usuario, null, 
            Engajamento.TipoEngajamento.POSTAGEM, 50, LocalDateTime.now());
        assertEquals(50, completo.getPontos());
    }

    @Test
    @DisplayName("Deve definir tipo e calcular pontos automaticamente")
    void deveDefinirTipoECalcularPontos() {
        engajamento.definirTipo(Engajamento.TipoEngajamento.POSTAGEM);
        assertEquals(Engajamento.TipoEngajamento.POSTAGEM, engajamento.getTipo());
        assertEquals(50, engajamento.getPontos());
    }

    @Test
    @DisplayName("Deve executar onCreate e inicializar campos")
    void deveExecutarOnCreateEInicializarCampos() {
        Engajamento novo = new Engajamento();
        novo.setTipo(Engajamento.TipoEngajamento.COMENTARIO);
        novo.onCreate();
        
        assertNotNull(novo.getDataEngajamento());
        assertEquals(20, novo.getPontos());
        
        novo.setPontos(999);
        novo.onCreate();
        assertEquals(999, novo.getPontos());
    }

    @Test
    @DisplayName("Deve testar enum TipoEngajamento e pontos")
    void deveTestarEnumTipoEngajamentoEPontos() {
        assertEquals(50, Engajamento.TipoEngajamento.POSTAGEM.getPontos());
        assertEquals(30, Engajamento.TipoEngajamento.RESPOSTA.getPontos());
        assertEquals(10, Engajamento.TipoEngajamento.CURTIDA.getPontos());
        assertEquals(20, Engajamento.TipoEngajamento.COMENTARIO.getPontos());
        assertEquals(15, Engajamento.TipoEngajamento.COMPARTILHAMENTO.getPontos());
        assertEquals(5, Engajamento.TipoEngajamento.values().length);
    }

    @Test
    @DisplayName("Deve testar equals, hashCode e toString")
    void deveTestarEqualsHashCodeToString() {
        Engajamento e1 = new Engajamento();
        e1.setId(1L);
        Engajamento e2 = new Engajamento();
        e2.setId(1L);
        
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertTrue(engajamento.toString().contains("Engajamento"));
    }
}