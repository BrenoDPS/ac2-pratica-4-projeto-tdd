package com.example.tdd_projeto.domain.repository;

import com.example.tdd_projeto.domain.entity.Usuario;
import com.example.tdd_projeto.domain.valueobject.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório de Usuários.
 * Interface que o Spring Data JPA implementa automaticamente.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca usuário por email
     */
    Optional<Usuario> findByEmail_Endereco(String endereco);
    
    /**
     * Lista todos usuários ativos
     */
    List<Usuario> findByAtivoTrue();
    
    /**
     * Lista usuários ordenados por pontuação (maior para menor)
     */
    @Query("SELECT u FROM Usuario u WHERE u.ativo = true ORDER BY u.pontuacaoTotal DESC")
    List<Usuario> findTopUsuariosByPontuacao();
    
    /**
     * Conta quantos usuários têm pontuação maior que o valor informado
     */
    Long countByPontuacaoTotalGreaterThan(Integer pontuacao);
}