package com.example.tdd_projeto.domain.valueobject;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.regex.Pattern;

/**
 * Value Object que representa um email válido. Imutável e contém sua própria validação.
*/
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA precisa
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // Usar factory method
@EqualsAndHashCode
@ToString
public class Email {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    @NotBlank(message = "Email não pode ser vazio")
    private String endereco;
    
    /**
     * Factory method para criar Email validado
     */
    public static Email de(String endereco) {
        validar(endereco);
        return new Email(endereco);
    }
    
    private static void validar(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        if (!EMAIL_PATTERN.matcher(endereco).matches()) {
            throw new IllegalArgumentException("Email inválido: " + endereco);
        }
    }
}