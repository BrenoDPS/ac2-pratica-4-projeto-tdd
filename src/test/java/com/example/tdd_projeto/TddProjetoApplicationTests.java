package com.example.tdd_projeto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Testes de Contexto da Aplicacao")
class TddProjetoApplicationTests {

    @Test
    @DisplayName("Deve carregar contexto")
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }

    @Test
    @DisplayName("Deve inicializar classe da aplicacao")
    void shouldInstantiateApplicationClass() {
        TddProjetoApplication app = new TddProjetoApplication();
        assertThat(app).isNotNull();
    }
}