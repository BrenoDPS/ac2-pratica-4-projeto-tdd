package com.example.tdd_projeto.infrastructure.service;

import com.example.tdd_projeto.domain.entity.Usuario;
import com.example.tdd_projeto.domain.repository.UsuarioRepository;
import com.example.tdd_projeto.domain.valueobject.Email;
import com.example.tdd_projeto.shared.dto.UsuarioDTO;
import com.example.tdd_projeto.shared.exception.BusinessException;
import com.example.tdd_projeto.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do UsuarioServiceImpl usando Mockito
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioService")
class UsuarioServiceImplTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioServiceImpl usuarioService;
    
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;
    
    @BeforeEach
    void setUp() {
        usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email(Email.de("joao@teste.com"))
                .pontuacaoTotal(100)
                .ativo(true)
                .build();
        
        usuarioDTO = UsuarioDTO.builder()
                .nome("João Silva")
                .email("joao@teste.com")
                .pontuacaoTotal(100)
                .ativo(true)
                .build();
    }
    
    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        // GIVEN
        when(usuarioRepository.findByEmail_Endereco(anyString()))
            .thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuario);
        
        // WHEN
        UsuarioDTO resultado = usuarioService.criar(usuarioDTO);
        
        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("João Silva");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email duplicado")
    void deveLancarExcecaoAoCriarUsuarioComEmailDuplicado() {
        // GIVEN
        when(usuarioRepository.findByEmail_Endereco(anyString()))
            .thenReturn(Optional.of(usuario));
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.criar(usuarioDTO))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("já está cadastrado");
        
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    
    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorIdComSucesso() {
        // GIVEN
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        
        // WHEN
        UsuarioDTO resultado = usuarioService.buscarPorId(1L);
        
        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("João Silva");
        verify(usuarioRepository, times(1)).findById(1L);
    }
    
    @Test
    @DisplayName("Deve lançar exceção ao buscar usuário inexistente")
    void deveLancarExcecaoAoBuscarUsuarioInexistente() {
        // GIVEN
        when(usuarioRepository.findById(999L))
            .thenReturn(Optional.empty());
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.buscarPorId(999L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("não encontrado");
    }
    
    @Test
    @DisplayName("Deve listar usuários ativos")
    void deveListarUsuariosAtivos() {
        // GIVEN
        Usuario usuario2 = Usuario.builder()
                .id(2L)
                .nome("Maria Santos")
                .email(Email.de("maria@teste.com"))
                .ativo(true)
                .build();
        
        when(usuarioRepository.findByAtivoTrue())
            .thenReturn(Arrays.asList(usuario, usuario2));
        
        // WHEN
        List<UsuarioDTO> resultado = usuarioService.listarAtivos();
        
        // THEN
        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(UsuarioDTO::getNome)
            .containsExactlyInAnyOrder("João Silva", "Maria Santos");
    }
    
    @Test
    @DisplayName("Deve atualizar usuário com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        // GIVEN
        UsuarioDTO atualizacaoDTO = UsuarioDTO.builder()
                .nome("João Silva Atualizado")
                .email("joao@teste.com")
                .build();
        
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuario);
        
        // WHEN
        UsuarioDTO resultado = usuarioService.atualizar(1L, atualizacaoDTO);
        
        // THEN
        assertThat(resultado).isNotNull();
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
    
    @Test
    @DisplayName("Deve desativar usuário com sucesso")
    void deveDesativarUsuarioComSucesso() {
        // GIVEN
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuario);
        
        // WHEN
        usuarioService.desativar(1L);
        
        // THEN
        assertThat(usuario.getAtivo()).isFalse();
        verify(usuarioRepository, times(1)).save(usuario);
    }
    
    @Test
    @DisplayName("Deve adicionar pontos ao usuário")
    void deveAdicionarPontosAoUsuario() {
        // GIVEN
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuario);
        
        int pontosIniciais = usuario.getPontuacaoTotal();
        
        // WHEN
        UsuarioDTO resultado = usuarioService.adicionarPontos(1L, 50);
        
        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getPontuacaoTotal()).isEqualTo(pontosIniciais + 50);
        assertThat(usuario.getPontuacaoTotal()).isEqualTo(pontosIniciais + 50);
        verify(usuarioRepository, times(1)).save(usuario);
    }
    
    @Test
    @DisplayName("Deve listar top usuários por pontuação")
    void deveListarTopUsuariosPorPontuacao() {
        // GIVEN
        Usuario usuario2 = Usuario.builder()
                .id(2L)
                .nome("Maria Santos")
                .email(Email.de("maria@teste.com"))
                .pontuacaoTotal(200)
                .ativo(true)
                .build();
        
        when(usuarioRepository.findTopUsuariosByPontuacao())
            .thenReturn(Arrays.asList(usuario2, usuario)); // Ordenado por pontuação
        
        // WHEN
        List<UsuarioDTO> resultado = usuarioService.listarTopPorPontuacao();
        
        // THEN
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getPontuacaoTotal()).isGreaterThan(
            resultado.get(1).getPontuacaoTotal()
        );
    }

    @Test
    @DisplayName("Deve criar usuário com email null")
    void deveCriarUsuarioComEmailNull() {
        // GIVEN
        UsuarioDTO dtoSemEmail = UsuarioDTO.builder()
                .nome("Pedro Santos")
                .email(null)
                .build();
        
        Usuario usuarioSemEmail = Usuario.builder()
                .id(3L)
                .nome("Pedro Santos")
                .email(null)
                .build();
        
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuarioSemEmail);
        
        // WHEN
        UsuarioDTO resultado = usuarioService.criar(dtoSemEmail);
        
        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Pedro Santos");
        verify(usuarioRepository, never()).findByEmail_Endereco(anyString());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar usuário inexistente")
    void deveLancarExcecaoAoAtualizarUsuarioInexistente() {
        // GIVEN
        UsuarioDTO atualizacaoDTO = UsuarioDTO.builder()
                .nome("Novo Nome")
                .build();
        
        when(usuarioRepository.findById(999L))
            .thenReturn(Optional.empty());
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.atualizar(999L, atualizacaoDTO))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("não encontrado");
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar para email já existente")
    void deveLancarExcecaoAoAtualizarParaEmailJaExistente() {
        // GIVEN
        Usuario outroUsuario = Usuario.builder()
                .id(2L)
                .nome("Maria")
                .email(Email.de("maria@teste.com"))
                .build();
        
        UsuarioDTO atualizacaoDTO = UsuarioDTO.builder()
                .nome("João Silva")
                .email("maria@teste.com") // Tentar mudar para email de outro usuário
                .build();
        
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByEmail_Endereco("maria@teste.com"))
            .thenReturn(Optional.of(outroUsuario));
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.atualizar(1L, atualizacaoDTO))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("já está cadastrado");
    }

    @Test
    @DisplayName("Deve atualizar sem verificar email quando mantém o mesmo")
    void deveAtualizarSemVerificarEmailQuandoMantemMesmo() {
        // GIVEN
        UsuarioDTO atualizacaoDTO = UsuarioDTO.builder()
                .nome("João Silva Atualizado")
                .email("joao@teste.com") // Mesmo email
                .build();
        
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuario);
        
        // WHEN
        UsuarioDTO resultado = usuarioService.atualizar(1L, atualizacaoDTO);
        
        // THEN
        assertThat(resultado).isNotNull();
        // Não deve chamar findByEmail_Endereco porque o email não mudou
        verify(usuarioRepository, never()).findByEmail_Endereco(anyString());
    }

    @Test
    @DisplayName("Deve atualizar quando DTO tem email diferente e novo email está disponível")
    void deveAtualizarQuandoEmailDiferenteEstaDisponivel() {
        // GIVEN
        UsuarioDTO atualizacaoDTO = UsuarioDTO.builder()
                .nome("João Silva")
                .email("novo.email@teste.com") // Email diferente
                .build();
        
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        when(usuarioRepository.findByEmail_Endereco("novo.email@teste.com"))
            .thenReturn(Optional.empty()); // Email disponível
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuario);
        
        // WHEN
        UsuarioDTO resultado = usuarioService.atualizar(1L, atualizacaoDTO);
        
        // THEN
        assertThat(resultado).isNotNull();
        verify(usuarioRepository, times(1)).findByEmail_Endereco("novo.email@teste.com");
    }

    @Test
    @DisplayName("Deve atualizar quando email no DTO é null")
    void deveAtualizarQuandoEmailNoDTONull() {
        // GIVEN
        UsuarioDTO atualizacaoDTO = UsuarioDTO.builder()
                .nome("João Silva Atualizado")
                .email(null) // Email null
                .build();
        
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class)))
            .thenReturn(usuario);
        
        // WHEN
        UsuarioDTO resultado = usuarioService.atualizar(1L, atualizacaoDTO);
        
        // THEN
        assertThat(resultado).isNotNull();
        verify(usuarioRepository, never()).findByEmail_Endereco(anyString());
    }

    @Test
    @DisplayName("Deve lançar exceção ao desativar usuário inexistente")
    void deveLancarExcecaoAoDesativarUsuarioInexistente() {
        // GIVEN
        when(usuarioRepository.findById(999L))
            .thenReturn(Optional.empty());
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.desativar(999L))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("não encontrado");
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar pontos a usuário inexistente")
    void deveLancarExcecaoAoAdicionarPontosUsuarioInexistente() {
        // GIVEN
        when(usuarioRepository.findById(999L))
            .thenReturn(Optional.empty());
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.adicionarPontos(999L, 50))
            .isInstanceOf(ResourceNotFoundException.class)
            .hasMessageContaining("não encontrado");
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar pontos negativos")
    void deveLancarExcecaoAoAdicionarPontosNegativos() {
        // GIVEN
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.adicionarPontos(1L, -50))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("positivos");
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar pontos null")
    void deveLancarExcecaoAoAdicionarPontosNull() {
        // GIVEN
        when(usuarioRepository.findById(1L))
            .thenReturn(Optional.of(usuario));
        
        // WHEN & THEN
        assertThatThrownBy(() -> usuarioService.adicionarPontos(1L, null))
            .isInstanceOf(IllegalArgumentException.class);
    }
}