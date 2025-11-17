package com.example.tdd_projeto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Testes de Contexto da Aplicação")
class TddProjetoApplicationTests {

    @Test
    @DisplayName("Deve carregar contexto")
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }

    @Test
    @DisplayName("Deve executar main")
    void shouldRunMain() {
        TddProjetoApplication.main(new String[]{});
    }

    @Test
    @DisplayName("Deve instanciar classe")
    void shouldInstantiate() {
        assertThat(new TddProjetoApplication()).isNotNull();
    }
}