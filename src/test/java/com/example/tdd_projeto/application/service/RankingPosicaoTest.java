package com.example.tdd_projeto.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para RankingPosicao")
class RankingPosicaoTest {

    @Test
    @DisplayName("Deve criar RankingPosicao com todos os campos")
    void deveCriarComTodosCampos() {
        RankingPosicao ranking = new RankingPosicao(1, 500, "+2", "semanal");
        
        assertEquals(1, ranking.getPosicao());
        assertEquals(500, ranking.getPontuacao());
        assertEquals("+2", ranking.getVariacao());
        assertEquals("semanal", ranking.getPeriodo());
    }

    @Test
    @DisplayName("Deve testar getters")
    void deveTestarGetters() {
        RankingPosicao ranking = new RankingPosicao(5, 250, "-1", "mensal");
        
        assertNotNull(ranking);
        assertEquals(5, ranking.getPosicao());
        assertEquals(250, ranking.getPontuacao());
        assertEquals("-1", ranking.getVariacao());
        assertEquals("mensal", ranking.getPeriodo());
    }
}