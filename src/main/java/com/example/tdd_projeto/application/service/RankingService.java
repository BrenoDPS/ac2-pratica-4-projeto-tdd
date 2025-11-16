package com.example.tdd_projeto.application.service;

import java.util.List;

public class RankingService {

    private static final int PONTOS_BASE = 100;
    private static final int BONUS_ENGAJAMENTO = 50;
    private static final String VARIACAO_PADRAO = "+2";

    public RankingPosicao buscarRanking(Usuario usuario, String periodo) {
        int pontuacao = calcularPontuacao(usuario.getEngajamentos());
        int posicao = calcularPosicao(usuario.getEngajamentos());
        String variacao = calcularVariacao();

        return new RankingPosicao(posicao, pontuacao, variacao, periodo);
    }

    private int calcularPontuacao(List<String> engajamentos) {
        return PONTOS_BASE + (engajamentos.size() * BONUS_ENGAJAMENTO);
    }

    private int calcularPosicao(List<String> engajamentos) {
        // Simples: se tiver algum engajamento, posição = 1, senão = 0
        return engajamentos.isEmpty() ? 0 : 1;
    }

    private String calcularVariacao() {
        // Por enquanto fixa, mas pode evoluir para lógica real
        return VARIACAO_PADRAO;
    }
}
