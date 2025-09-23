package com.example.tdd_projeto.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RankingServiceTest {

    // BDD 1 - Ranking Semanal
    @Test
    void deveExibirPosicaoEVariacaoNoRankingSemanal() {
        // Arrange
        Usuario aluno = new Usuario("joao");
        RankingService rankingService = new RankingService();

        // Act
        RankingPosicao posicao = rankingService.buscarRanking(aluno, "semanal");

        // Assert
        assertNotNull(posicao);
        assertEquals(0, posicao.getPosicao());
        assertEquals(100, posicao.getPontuacao());
        assertEquals("+2", posicao.getVariacao());
    }

    // BDD 2 - Ranking Mensal
    @Test
    void deveAtualizarRankingQuandoSelecionadoFiltroMensal() {
        Usuario aluno = new Usuario("maria");
        RankingService rankingService = new RankingService();

        RankingPosicao posicao = rankingService.buscarRanking(aluno, "mensal");

        assertNotNull(posicao);
        assertEquals("mensal", posicao.getPeriodo());
    }

    // BDD 3 - Atualização após engajamento
    @Test
    void deveRecalcularPontuacaoAposAcoesDeEngajamento() {
        Usuario aluno = new Usuario("ana");
        aluno.registrarEngajamento("postagem");
        RankingService rankingService = new RankingService();

        RankingPosicao posicao = rankingService.buscarRanking(aluno, "semanal");

        assertTrue(posicao.getPontuacao() > 0);
    }

    // BDD 4 - Visualização do Ranking
    @Test
    void deveExibirPosicionamentoQuandoAlunoParticipaDaComunidade() {
        Usuario aluno = new Usuario("carlos");
        aluno.registrarEngajamento("postagem");
        aluno.registrarEngajamento("resposta");
        RankingService rankingService = new RankingService();

        RankingPosicao posicao = rankingService.buscarRanking(aluno, "semanal");

        assertNotNull(posicao);
        assertTrue(posicao.getPosicao() > 0);
    }
}
