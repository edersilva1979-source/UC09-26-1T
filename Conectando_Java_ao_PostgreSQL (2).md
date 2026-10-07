# Conectando Java ao PostgreSQL

## Objetivo da aula

Nesta aula, eu vou mostrar como fazer o Java se comunicar com o banco de
dados PostgreSQL.

Até agora, nós trabalhamos com dados guardados em memória, usando listas
como `ArrayList`. Isso funciona para estudar lógica, classes, objetos e
telas, mas tem um problema: quando fechamos o sistema, os dados
desaparecem.

Agora vamos dar um passo importante. Vamos fazer o nosso sistema salvar
e buscar informações em um banco de dados real.

------------------------------------------------------------------------

# 1. O que é JDBC?

JDBC significa **Java Database Connectivity**.

De forma simples, eu gosto de explicar assim:

O JDBC é a ponte entre o Java e o banco de dados.

``` text
Java → JDBC → PostgreSQL
```

Quando clicamos em um botão do Java Swing, o Java envia comandos SQL ao
PostgreSQL e recebe as respostas.

------------------------------------------------------------------------

# 2. O que é o Driver PostgreSQL?

O driver é uma biblioteca que permite que o Java converse com o
PostgreSQL.

Sem ele, a conexão não é possível.

``` text
Java
   ↓
Driver PostgreSQL
   ↓
Banco de Dados
```

------------------------------------------------------------------------

# 3. Preparando o projeto

## Criando o projeto

Nome do projeto:

``` text
aula03conexao
```

## Adicionando o driver

No NetBeans:

``` text
Projeto
→ Properties
→ Libraries
→ Classpath
→ Add JAR/Folder
→ Selecione o driver PostgreSQL
```

------------------------------------------------------------------------

# 4. Criando o banco

``` sql
CREATE DATABASE escola_26_1T;
```

Tabela:

``` sql
CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    turma VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE
);
```

------------------------------------------------------------------------

# 5. Classe Conexao

``` java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection conectar() {

        Connection conexao = null;

        String url = "jdbc:postgresql://localhost/escola_26_1T";
        String usuario = "postgres";
        String senha = "root";

        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão realizada com sucesso!");
        } catch (SQLException erro) {
            System.out.println("Erro ao conectar.");
            System.out.println(erro.getMessage());
        }

        return conexao;
    }
}
```

## Explicação

-   `Connection` representa a conexão com o banco.
-   `DriverManager` abre a conexão.
-   `SQLException` trata erros.
-   `conectar()` retorna uma conexão pronta para uso.

------------------------------------------------------------------------

# 6. Testando a conexão

``` java
public class TesteConexao {

    public static void main(String[] args) {
        Conexao.conectar();
    }

}
```

Resultado esperado:

``` text
Conexão realizada com sucesso!
```

------------------------------------------------------------------------

# 7. Statement

O `Statement` executa comandos SQL simples.

Exemplo:

``` java
Statement stmt = conexao.createStatement();

stmt.executeUpdate(
    "INSERT INTO aluno(nome,turma,email) VALUES ('Ana','Turma A','ana@email.com')"
);
```

Embora funcione, não é a opção mais segura quando os dados vêm do
usuário.

------------------------------------------------------------------------

# 8. PreparedStatement

É a forma recomendada de executar comandos SQL.

``` java
String sql = "INSERT INTO aluno(nome,turma,email) VALUES (?, ?, ?)";

PreparedStatement stmt = conexao.prepareStatement(sql);

stmt.setString(1,"Carlos");
stmt.setString(2,"Turma B");
stmt.setString(3,"carlos@email.com");

stmt.executeUpdate();
```

### Vantagens

-   Mais seguro
-   Evita SQL Injection
-   Código mais organizado
-   Melhor desempenho em comandos repetitivos

------------------------------------------------------------------------

# 9. ResultSet

Usado para receber os dados de um `SELECT`.

``` java
PreparedStatement stmt =
    conexao.prepareStatement("SELECT * FROM aluno");

ResultSet resultado = stmt.executeQuery();

while(resultado.next()){

    System.out.println(resultado.getInt("id"));
    System.out.println(resultado.getString("nome"));

}
```

------------------------------------------------------------------------

# 10. Fechando conexões

Sempre feche os recursos utilizados.

``` java
resultado.close();
stmt.close();
conexao.close();
```

Boa prática:

``` text
ResultSet
↓
PreparedStatement
↓
Connection
```

------------------------------------------------------------------------

# 11. Tratamento de exceções

Sempre utilize `try/catch`.

``` java
try{

    // código

}catch(SQLException erro){

    System.out.println(erro.getMessage());

}
```

Alguns erros comuns:

-   Senha incorreta
-   Banco inexistente
-   Driver não instalado
-   PostgreSQL desligado
-   SQL incorreto

------------------------------------------------------------------------

# 12. Fluxo completo

``` text
Java Swing
      ↓
Objeto
      ↓
Conexao
      ↓
PreparedStatement
      ↓
PostgreSQL
      ↓
ResultSet
      ↓
Java
```

------------------------------------------------------------------------

# 13. Atividade prática

1.  Criar o banco `escola`.
2.  Criar a tabela `aluno`.
3.  Criar a classe `Conexao`.
4.  Testar a conexão.
5.  Criar um `PreparedStatement` para inserir um aluno.
6.  Criar um `SELECT` usando `ResultSet`.
7.  Exibir os registros no console.

------------------------------------------------------------------------

# 14. Desafio

Crie uma tabela `produto` e desenvolva uma classe `ProdutoDAO` contendo:

-   `adicionarProduto()`
-   `listarProdutos()`

------------------------------------------------------------------------

# Resumo

Nesta aula aprendemos:

-   O que é JDBC.
-   Como instalar o driver PostgreSQL.
-   Como criar uma conexão.
-   Como utilizar `Connection`.
-   Como utilizar `Statement`.
-   Como utilizar `PreparedStatement`.
-   Como utilizar `ResultSet`.
-   Como tratar exceções.
-   Como fechar conexões corretamente.

Na próxima aula, iremos integrar tudo isso ao Java Swing, fazendo com
que os botões do sistema gravem e consultem informações diretamente no
PostgreSQL.
