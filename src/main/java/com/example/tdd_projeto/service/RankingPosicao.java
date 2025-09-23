package com.example.tdd_projeto.service;

public class RankingPosicao {
    private final int posicao;
    private final int pontuacao;
    private final String variacao;
    private final String periodo;

    public RankingPosicao(int posicao, int pontuacao, String variacao, String periodo) {
        this.posicao = posicao;
        this.pontuacao = pontuacao;
        this.variacao = variacao;
        this.periodo = periodo;
    }

    public int getPosicao() { return posicao; }
    public int getPontuacao() { return pontuacao; }
    public String getVariacao() { return variacao; }
    public String getPeriodo() { return periodo; }
}
