package com.example.tdd_projeto.shared.dto;

import com.example.tdd_projeto.domain.entity.Usuario;
import com.example.tdd_projeto.domain.valueobject.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para UsuarioMapper")
class UsuarioMapperTest {

    @Test
    @DisplayName("Deve converter Entity para DTO completo")
    void deveConverterEntityParaDTOCompleto() {
        LocalDateTime now = LocalDateTime.now();
        Usuario entity = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email(Email.de("joao@test.com"))
                .pontuacaoTotal(100)
                .dataCadastro(now)
                .ativo(true)
                .build();

        UsuarioDTO dto = UsuarioMapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("João Silva", dto.getNome());
        assertEquals("joao@test.com", dto.getEmail());
        assertEquals(100, dto.getPontuacaoTotal());
        assertEquals(now, dto.getDataCadastro());
        assertTrue(dto.getAtivo());
    }

    @Test
    @DisplayName("Deve retornar null quando entity é null")
    void deveRetornarNullQuandoEntityNull() {
        UsuarioDTO dto = UsuarioMapper.toDTO(null);
        assertNull(dto);
    }

    @Test
    @DisplayName("Deve converter Entity sem email para DTO")
    void deveConverterEntitySemEmail() {
        Usuario entity = Usuario.builder()
                .id(1L)
                .nome("Maria")
                .email(null)
                .build();

        UsuarioDTO dto = UsuarioMapper.toDTO(entity);

        assertNotNull(dto);
        assertNull(dto.getEmail());
    }

    @Test
    @DisplayName("Deve converter DTO para Entity completo")
    void deveConverterDTOParaEntityCompleto() {
        LocalDateTime now = LocalDateTime.now();
        UsuarioDTO dto = UsuarioDTO.builder()
                .id(1L)
                .nome("Ana Silva")
                .email("ana@test.com")
                .pontuacaoTotal(50)
                .dataCadastro(now)
                .ativo(false)
                .build();

        Usuario entity = UsuarioMapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Ana Silva", entity.getNome());
        assertEquals("ana@test.com", entity.getEmail().getEndereco());
        assertEquals(50, entity.getPontuacaoTotal());
        assertEquals(now, entity.getDataCadastro());
        assertFalse(entity.getAtivo());
    }

    @Test
    @DisplayName("Deve retornar null quando DTO é null")
    void deveRetornarNullQuandoDTONull() {
        Usuario entity = UsuarioMapper.toEntity(null);
        assertNull(entity);
    }

    @Test
    @DisplayName("Deve converter DTO sem email para Entity")
    void deveConverterDTOSemEmail() {
        UsuarioDTO dto = UsuarioDTO.builder()
                .nome("Pedro")
                .email(null)
                .build();

        Usuario entity = UsuarioMapper.toEntity(dto);

        assertNotNull(entity);
        assertNull(entity.getEmail());
    }

    @Test
    @DisplayName("Deve atualizar entity com todos os campos do DTO")
    void deveAtualizarEntityComTodosCamposDTO() {
        Usuario entity = Usuario.builder()
                .nome("Nome Antigo")
                .email(Email.de("antigo@test.com"))
                .ativo(true)
                .build();

        UsuarioDTO dto = UsuarioDTO.builder()
                .nome("Nome Novo")
                .email("novo@test.com")
                .ativo(false)
                .build();

        UsuarioMapper.updateEntityFromDTO(entity, dto);

        assertEquals("Nome Novo", entity.getNome());
        assertEquals("novo@test.com", entity.getEmail().getEndereco());
        assertFalse(entity.getAtivo());
    }

    @Test
    @DisplayName("Deve atualizar entity apenas com campos não nulos")
    void deveAtualizarEntityApenasComCamposNaoNulos() {
        Usuario entity = Usuario.builder()
                .nome("Nome Original")
                .email(Email.de("original@test.com"))
                .ativo(true)
                .build();

        UsuarioDTO dto = UsuarioDTO.builder()
                .nome(null)
                .email(null)
                .ativo(null)
                .build();

        UsuarioMapper.updateEntityFromDTO(entity, dto);

        // Não deve alterar nada se todos os campos são null
        assertEquals("Nome Original", entity.getNome());
        assertEquals("original@test.com", entity.getEmail().getEndereco());
        assertTrue(entity.getAtivo());
    }

    @Test
    @DisplayName("Deve atualizar apenas nome quando outros campos são null")
    void deveAtualizarApenasNome() {
        Usuario entity = Usuario.builder()
                .nome("Nome Velho")
                .email(Email.de("email@test.com"))
                .build();

        UsuarioDTO dto = UsuarioDTO.builder()
                .nome("Nome Atualizado")
                .email(null)
                .ativo(null)
                .build();

        UsuarioMapper.updateEntityFromDTO(entity, dto);

        assertEquals("Nome Atualizado", entity.getNome());
    }

    @Test
    @DisplayName("Deve instanciar UsuarioMapper")
    void deveInstanciarUsuarioMapper() {
        UsuarioMapper mapper = new UsuarioMapper();
        assertNotNull(mapper);
    }
}