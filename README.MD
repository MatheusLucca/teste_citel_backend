# Sistema de Banco de Sangue - Back-end

Este projeto é o back-end para um sistema de gerenciamento de banco de sangue, desenvolvido como parte de um teste técnico. A aplicação web baseada em Spring Boot processa dados de candidatos a doadores de sangue e fornece análises estatísticas através de uma API REST.

## 📋 Sobre o Projeto

O sistema processa dados de candidatos a doadores de sangue e apresenta análises estatísticas importantes, incluindo:

- Distribuição de candidatos por estado brasileiro
- IMC médio por faixa etária (intervalos de 10 anos)
- Percentual de obesos entre homens e mulheres
- Média de idade por tipo sanguíneo
- Quantitativo de possíveis doadores para cada tipo sanguíneo receptor

> **Nota:** Somente pessoas com idade entre 16 a 69 anos e com peso acima de 50 Kg podem doar sangue.

## 🚀 Tecnologias Utilizadas

Este projeto foi desenvolvido com as seguintes tecnologias:

- **Java 21** - Linguagem de programação
- **Spring Boot 3.4.5** - Framework para criação de aplicações Java
- **Spring Data JPA** - Simplificação do acesso a dados com JPA
- **MySQL** - Banco de dados relacional
- **Maven** - Gerenciamento de dependências e build

## 🔧 Instalação e Configuração

### Pré-requisitos

- JDK 21
- Maven 3.8+
- MySQL 8.0+
- Git (opcional)

### Configuração do Banco de Dados

1. Certifique-se de que o MySQL está instalado e em execução.
2. O banco de dados `banco_sangue` será criado automaticamente quando a aplicação for executada pela primeira vez.
3. Configure as credenciais do banco de dados no arquivo `application.properties` ou use variáveis de ambiente:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banco_sangue?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Para substituir as variáveis de ambiente, você pode:

- Definir as variáveis no seu sistema:
  ```bash
  export DB_USERNAME=seu_usuario
  export DB_PASSWORD=sua_senha
  ```
- Ou modificar diretamente o arquivo `application.properties`:
  ```properties
  spring.datasource.username=seu_usuario
  spring.datasource.password=sua_senha
  ```

### Instalação

1. Clone o repositório:
   ```bash
   git clone <url-do-repositorio>
   cd <nome-do-diretorio>
   ```

2. Compile o projeto:
   ```bash
   mvn clean install
   ```

3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

   Alternativamente, após a compilação, você pode executar o JAR gerado:
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

4. Usando variáveis de ambiente para configurar o banco de dados:
   ```bash
   DB_USERNAME=seu_usuario DB_PASSWORD=sua_senha mvn spring-boot:run
   ```
   
   Ou
   ```bash
   DB_USERNAME=seu_usuario DB_PASSWORD=sua_senha java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

> **Importante:** O servidor roda na porta 8080 (http://localhost:8080). Certifique-se de que esta porta esteja disponível.

## 📝 Scripts SQL

O projeto inclui um arquivo `schema.sql` que é executado automaticamente durante a inicialização para criar a estrutura do banco de dados.

## 📱 Funcionalidades e Endpoints da API

O sistema disponibiliza os seguintes endpoints REST:

### 1. Gerenciamento de Doadores
- **POST** `/api/doadores`: Cadastrar novos doadores
  - Corpo da requisição: Lista de objetos `DoadorDTO` em formato JSON
  - Resposta: Lista de `DoadorDTO` cadastrados

- **GET** `/api/doadores`: Listar todos os doadores cadastrados
  - Resposta: Lista de `DoadorDTO`

### 2. Análise Estatística
- **GET** `/api/doadores/estatisticas`: Obter estatísticas detalhadas sobre os doadores
  - Resposta: Objeto `EstatisticasDTO` contendo:
    - Quantidade de candidatos por estado
    - IMC médio por faixa etária (de 10 em 10 anos)
    - Percentual de obesos entre homens e mulheres
    - Média de idade por tipo sanguíneo
    - Quantidade de possíveis doadores para cada tipo sanguíneo receptor

## 🔗 Integração com Frontend

A API foi projetada para integrar com o frontend através de requisições HTTP. O CORS está configurado para aceitar requisições de `http://localhost:3000`.

## 📜 Regras de Compatibilidade Sanguínea

| Tipo sanguíneo | Pode doar para | Pode receber de |
|----------------|----------------|-----------------|
| A+             | A+, AB+        | A+, A-, O+, O-  |
| A-             | A+, A-, AB+, AB- | A-, O-        |
| B+             | B+, AB+        | B+, B-, O+, O-  |
| B-             | B+, B-, AB+, AB- | B-, O-        |
| AB+            | AB+            | Todos           |
| AB-            | AB+, AB-       | A-, B-, O-, AB- |
| O+             | A+, B+, O+, AB+ | O+, O-         |
| O-             | Todos          | O-              |

## 🧪 Testes

### Executando os Testes

Para executar todos os testes do projeto:

```bash
mvn test
```

Para executar testes específicos:

```bash
mvn test -Dtest=NomeDaClasseDeTeste
```

### Testes Unitários Implementados

O projeto conta com testes unitários abrangentes que garantem a qualidade e robustez do código:

#### 1. Testes da Entidade (DoadorTest)
- Cálculo correto de idade baseado na data de nascimento
- Cálculo correto de IMC baseado em altura e peso
- Verificação de obesidade
- Verificação de elegibilidade para doação de sangue
- Cálculo de faixa etária
- Conversão de strings de data para LocalDate

#### 2. Testes de Mapeamento (DoadorMapperTest)
- Conversão de entidade Doador para DoadorDTO
- Conversão de DoadorDTO para entidade Doador
- Mapeamento de listas entre entidades e DTOs
- Formatação correta de datas nos DTOs

#### 3. Testes de Serviço (DoadorServiceTest)
- Salvamento de doadores
- Listagem de doadores
- Validação de CPF duplicado
- Geração de estatísticas:
  - Contagem de candidatos por estado
  - Cálculo de IMC médio por faixa etária
  - Cálculo de percentual de obesos entre homens e mulheres
  - Cálculo de idade média por tipo sanguíneo
  - Contagem de possíveis doadores por tipo sanguíneo receptor

Os testes utilizam JUnit 5 e Mockito para criar simulações (mocks) de componentes e verificar comportamentos esperados.

A cobertura de testes abrange os principais fluxos de negócio e cenários de borda, garantindo que a aplicação se comporte conforme esperado mesmo em situações excepcionais.

## ⚙️ Configuração CORS

A aplicação está configurada para permitir requisições CORS de `http://localhost:3000`. Se o frontend estiver rodando em outra porta ou host, você precisará modificar a classe `CorsConfig.java`:

```java
registry.addMapping("/**")
        .allowedOrigins("http://seu-frontend-url")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*");
```

## 📊 Modelo de Dados

### Doador
```java
public class Doador {
    private Long id;
    private String nome;
    private String cpf;          // Único no sistema
    private String rg;
    private LocalDate dataNascimento;
    private String sexo;         // "Masculino" ou "Feminino"
    private String mae;
    private String pai;
    private String email;
    private String cep;
    private String endereco;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;       // Sigla UF
    private String telefoneFixo;
    private String celular;
    private Double altura;       // Em metros
    private Double peso;         // Em kg
    private String tipoSanguineo;// "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
}
```

## ❓ Resolução de Problemas

### Erro de Conexão com o Banco

Se você encontrar erros de conexão com o banco de dados:

1. Verifique se o MySQL está em execução
2. Confirme se as credenciais estão corretas
3. Verifique se o usuário tem permissões suficientes

### Erro de CORS

Se encontrar erros de CORS ao acessar a API do frontend:

1. Verifique se a origem do frontend está corretamente configurada em `CorsConfig.java`
2. Certifique-se de que o navegador não está bloqueando requisições


## 📋 Maven Commands

- `mvn clean` - Limpa o diretório target
- `mvn compile` - Compila o código fonte
- `mvn test` - Executa os testes
- `mvn package` - Empacota o projeto em um JAR
- `mvn install` - Instala o pacote no repositório local
- `mvn spring-boot:run` - Executa a aplicação
