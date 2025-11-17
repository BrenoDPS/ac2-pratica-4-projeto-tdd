package com.example.tdd_projeto.domain.repository;

import com.example.tdd_projeto.domain.entity.Conteudo;
import com.example.tdd_projeto.domain.entity.Usuario;
import com.example.tdd_projeto.domain.valueobject.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para ConteudoRepository")
class ConteudoRepositoryTest {

    @Autowired
    private ConteudoRepository conteudoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve salvar e buscar conteúdo")
    void deveSalvarEBuscarConteudo() {
        Usuario autor = Usuario.builder()
                .nome("João Silva")
                .email(Email.de("joao@test.com"))
                .build();
        usuarioRepository.save(autor);

        Conteudo conteudo = Conteudo.builder()
                .autor(autor)
                .titulo("Tutorial Java")
                .texto("Conteúdo sobre Java")
                .tipo(Conteudo.TipoConteudo.TUTORIAL)
                .build();

        Conteudo salvo = conteudoRepository.save(conteudo);
        assertThat(salvo.getId()).isNotNull();
        assertThat(conteudoRepository.findById(salvo.getId())).isPresent();
    }
}