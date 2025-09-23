package com.example.tdd_projeto.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
    private String nome;
    private Map<String, Integer> engajamentos = new HashMap<>();

    public Usuario(String nome) {
        this.nome = nome;
    }

    public void registrarEngajamento(String tipo) {
        engajamentos.put(tipo, engajamentos.getOrDefault(tipo, 0) + 1);
    }

    public int getPontuacaoTotal() {
        // Cada engajamento soma pontos (postagem = 50, resposta = 30, default = 10)
        int pontos = 0;
        for (Map.Entry<String, Integer> entry : engajamentos.entrySet()) {
            String tipo = entry.getKey();
            int qtd = entry.getValue();
            switch (tipo) {
                case "postagem" -> pontos += 50 * qtd;
                case "resposta" -> pontos += 30 * qtd;
                default -> pontos += 10 * qtd;
            }
        }
        return pontos;
    }

    public String getNome() {
        return nome;
    }

    public List<String> getEngajamentos() {
        return (List<String>) Map.of();
    }
}
