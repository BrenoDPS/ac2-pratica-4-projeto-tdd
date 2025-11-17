package com.example.tdd_projeto.domain.entity;

import com.example.tdd_projeto.domain.valueobject.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para Entidade Ranking")
class RankingTest {

    private Usuario usuario;
    private Ranking ranking;

    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email(Email.de("joao@example.com"))
                .build();

        ranking = Ranking.builder()
                .id(1L)
                .usuario(usuario)
                .posicao(1)
                .pontuacao(500)
                .variacaoPosicao(2)
                .periodo(Ranking.PeriodoRanking.SEMANAL)
                .dataReferencia(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("Deve criar ranking e testar getters/setters")
    void deveCriarRankingETestarGettersSetters() {
        assertNotNull(ranking);
        assertEquals(1, ranking.getPosicao());
        assertEquals(500, ranking.getPontuacao());
        
        ranking.setPontuacao(1000);
        assertEquals(1000, ranking.getPontuacao());
    }

    @Test
    @DisplayName("Deve criar com construtores vazio e completo")
    void deveCriarComConstrutores() {
        Ranking vazio = new Ranking();
        assertNotNull(vazio);
        
        Ranking completo = new Ranking(1L, usuario, 5, 300, -2, 
            Ranking.PeriodoRanking.MENSAL, LocalDate.now(), LocalDateTime.now());
        assertEquals(300, completo.getPontuacao());
    }

    @Test
    @DisplayName("Deve formatar variação corretamente")
    void deveFormatarVariacao() {
        ranking.setVariacaoPosicao(5);
        assertEquals("+5", ranking.getVariacaoFormatada());
        
        ranking.setVariacaoPosicao(-3);
        assertEquals("-3", ranking.getVariacaoFormatada());
        
        ranking.setVariacaoPosicao(0);
        assertEquals("=", ranking.getVariacaoFormatada());
        
        ranking.setVariacaoPosicao(null);
        assertEquals("=", ranking.getVariacaoFormatada());
    }

    @Test
    @DisplayName("Deve verificar melhora de posição")
    void deveVerificarMelhoraDePosicao() {
        ranking.setVariacaoPosicao(3);
        assertTrue(ranking.melhorou());
        
        ranking.setVariacaoPosicao(-2);
        assertFalse(ranking.melhorou());
        
        ranking.setVariacaoPosicao(0);
        assertFalse(ranking.melhorou());
        
        ranking.setVariacaoPosicao(null);
        assertFalse(ranking.melhorou());
    }

    @Test
    @DisplayName("Deve executar onCreate")
    void deveExecutarOnCreate() {
        Ranking novo = new Ranking();
        novo.onCreate();
        assertNotNull(novo.getDataCalculo());
        
        LocalDateTime dataFixa = LocalDateTime.of(2024, 1, 1, 10, 0);
        novo.setDataCalculo(dataFixa);
        novo.onCreate();
        assertEquals(dataFixa, novo.getDataCalculo());
    }

    @Test
    @DisplayName("Deve testar enum PeriodoRanking")
    void deveTestarEnumPeriodoRanking() {
        assertEquals("Semanal", Ranking.PeriodoRanking.SEMANAL.getDescricao());
        assertEquals("Mensal", Ranking.PeriodoRanking.MENSAL.getDescricao());
        assertEquals(5, Ranking.PeriodoRanking.values().length);
    }

    @Test
    @DisplayName("Deve testar equals, hashCode e toString")
    void deveTestarEqualsHashCodeToString() {
        Ranking r1 = new Ranking();
        r1.setId(1L);
        Ranking r2 = new Ranking();
        r2.setId(1L);
        
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertTrue(ranking.toString().contains("Ranking"));
    }
}