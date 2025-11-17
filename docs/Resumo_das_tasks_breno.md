# Documentação de Modelagem - Domain & Persistence

**Responsável:** Breno  
**Data:** Novembro 2025  
**Projeto:** AC2 - Clean Architecture & DDD

## 1. Visão Geral da Arquitetura

Este projeto segue **Clean Architecture** com camadas bem definidas:

- **Domain (Núcleo)**: Entidades, Value Objects, Repositories (interfaces)
- **Application**: Serviços e casos de uso
- **Infrastructure**: Implementações técnicas (controllers, JPA)

## 2. Modelagem DDD

### 2.1 Entidades

#### Usuario
- **Identidade**: ID (Long, auto-incremento)
- **Atributos**: nome, email (VO), pontuacaoTotal, dataCadastro, ativo
- **Relacionamentos**: 
  - OneToMany com Conteudo (autor)
  - OneToMany com Engajamento
  - OneToMany com Ranking
- **Regras de negócio**:
  - Adicionar pontos (apenas valores positivos)
  - Ativar/desativar usuário

#### Conteudo
- **Identidade**: ID
- **Atributos**: titulo, texto, tipo (enum), dataPublicacao, visualizacoes
- **Relacionamentos**:
  - ManyToOne com Usuario (autor)
  - OneToMany com Engajamento
- **Regras**: Incrementar visualizações

#### Engajamento
- **Identidade**: ID
- **Atributos**: tipo (enum), pontos, dataEngajamento
- **Relacionamentos**:
  - ManyToOne com Usuario
  - ManyToOne com Conteudo
- **Regras**: Pontos calculados automaticamente pelo tipo

#### Ranking
- **Identidade**: ID
- **Atributos**: posicao, pontuacao, variacaoPosicao, periodo (enum), dataReferencia
- **Relacionamentos**: ManyToOne com Usuario
- **Constraints**: Unique(usuario_id, periodo, data_referencia)

### 2.2 Value Objects

#### Email
- **Imutável**: Sem setters
- **Auto-validação**: Regex para formato de email
- **Factory Method**: `Email.de(String)` garante criação válida

## 3. Por que Entity precisa de Getters/Setters/Construtores/ToString/HashCode?

### 3.1 Getters e Setters
**Motivo técnico:**
- Frameworks (Spring, Hibernate) usam **reflexão** para acessar campos privados
- JPA precisa de getters para serializar dados
- Setters permitem frameworks popularem objetos

**Motivo arquitetural:**
- Encapsulamento: controle de acesso aos campos
- Permite validações e lógica ao modificar valores

### 3.2 Construtores
**Construtor vazio (`@NoArgsConstructor`):**
- **Obrigatório para JPA**: Hibernate precisa instanciar objetos via reflexão
- Usado em desserialização (JSON → Objeto)

**Construtor com parâmetros (`@AllArgsConstructor`):**
- Facilita criação de objetos válidos
- Garante campos obrigatórios na construção

### 3.3 ToString
**Motivo:**
- **Debugging**: Ver estado do objeto em logs
- **Rastreamento**: Identificar problemas em produção

**Cuidado:**
- Excluir campos sensíveis (senha, token)
- Excluir relacionamentos (evitar lazy loading exception)

### 3.4 HashCode e Equals
**Motivo técnico:**
- **Coleções** (Set, Map) usam hashCode para armazenar/buscar
- JPA usa equals para comparar objetos em cache (Persistence Context)

**Estratégia:**
- Comparar apenas por **ID** (`@EqualsAndHashCode(onlyExplicitlyIncluded = true)`)
- Consistência: objetos iguais → mesmo hashCode

**Problema se não tiver:**
- Set pode conter "duplicatas"
- Cache JPA pode falhar
- Bugs difíceis de rastrear em produção

## 4. Configuração de Profiles

### Development (application-dev.properties)
- Banco H2 em memória
- SQL visível em logs
- H2 Console habilitado
- `ddl-auto=create-drop` (recria schema a cada inicialização)

### Test (application-test.properties)
- Banco H2 separado (testdb)
- Logs reduzidos
- Isolamento entre testes

## 5. Mapeamento JPA

### Relacionamentos
- **@OneToMany / @ManyToOne**: Configurados com FetchType.LAZY (performance)
- **@JoinColumn**: Define colunas FK explicitamente
- **Cascade**: ALL para composições, PERSIST/MERGE para agregações
- **orphanRemoval**: true para dependentes (Engajamento de Conteudo)

### Constraints
- **@UniqueConstraint**: Ranking não duplicado por usuário/período
- **nullable=false**: Campos obrigatórios
- **unique=true**: Email único por usuário

## 6. Repositories (Spring Data JPA)

### Convenções de Nomes
- `findByAtivoTrue()` → gerado automaticamente
- `findByEmail_Endereco()` → navega em Value Object embutido

### Queries Customizadas (@Query)
- JPQL para queries complexas
- Performance: índices automáticos em FKs

## 7. Testes

### Cobertura Implementada
- **UsuarioRepositoryTest**: 8 testes (CRUD, queries, ordenação)
- **UsuarioTest**: 7 testes (regras de negócio, validações)
- **EmailTest**: 10 testes (validação, imutabilidade)

### Estratégia
- **@DataJpaTest**: Testes de integração com banco H2
- **Testes unitários**: Regras de negócio isoladas
- **AAA Pattern**: Arrange-Act-Assert

## 8. DTOs e Separação de Camadas

### Por que DTOs?
- **Segurança**: Não expor entidades na API
- **Performance**: Não carregar relacionamentos desnecessários
- **Flexibilidade**: API independente do modelo de dados

### Mappers
- Conversão manual Entity ↔ DTO
- Controle total sobre mapeamento
- Alternativa: MapStruct (geração automática)

## 9. Evidências

### Compilação
```bash
mvn clean compile
[INFO] BUILD SUCCESS
```

### Testes
```bash
mvn clean test
Tests run: 25, Failures: 0, Errors: 0
```

### Jacoco Coverage
- **Pacote domain.entity**: 95%
- **Pacote domain.repository**: 100%
- **Pacote domain.valueobject**: 98%

## 10. Conclusão

A camada de domínio foi modelada seguindo princípios DDD e Clean Architecture:
- Entidades com regras de negócio encapsuladas
- Value Objects imutáveis e auto-validáveis
- Repositories como contratos do domínio
- Separação clara entre camadas
- Testes garantindo qualidade e cobertura

---

**Próximos passos (Diogo e Raphael):**
- Implementar Services e Controllers (Membro B)
- Configurar pipeline Jenkins e testes adicionais (Membro C)