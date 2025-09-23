package com.example.tdd_projeto.service;

import java.util.ArrayList;
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

    // 🔧 Corrigido: gera uma lista de engajamentos baseada no map
    public List<String> getEngajamentos() {
        List<String> lista = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : engajamentos.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                lista.add(entry.getKey()); // adiciona o tipo várias vezes
            }
        }
        return lista;
    }
}
