# PreparedStatement com Scanner e PostgreSQL

## Objetivo da aula

Nesta aula, nós vamos melhorar o uso do `PreparedStatement` fazendo com que o aluno digite os dados diretamente no terminal.

Até agora, nós utilizamos valores fixos dentro do código.

Exemplo:

```java
String sql = "INSERT INTO aluno(nome, turma, email) VALUES (?, ?, ?)";

PreparedStatement stmt = conexao.prepareStatement(sql);

stmt.setString(1, "Carlos");
stmt.setString(2, "Turma B");
stmt.setString(3, "carlos@email.com");

stmt.executeUpdate();
```

Esse código funciona, mas os dados estão definidos diretamente no programa.

Agora vamos avançar.

Nós vamos permitir que o usuário digite:

```text
Nome
Turma
E-mail
```

Depois, o Java vai receber essas informações e gravá-las no PostgreSQL.

O fluxo será:

```text
Usuário
   ↓
Scanner
   ↓
Variáveis Java
   ↓
PreparedStatement
   ↓
PostgreSQL
```

---

# 1. Estrutura da tabela

Vamos continuar utilizando a tabela `aluno`.

```sql
CREATE TABLE aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    turma VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE
);
```

Observe que o campo `id` utiliza:

```sql
SERIAL
```

Isso significa que o PostgreSQL gera o código automaticamente.

Por isso, nós não precisamos pedir que o usuário digite o `id`.

Vamos solicitar somente:

```text
Nome
Turma
E-mail
```

---

# 2. O que é Scanner?

O `Scanner` é uma classe do Java utilizada para receber dados digitados pelo usuário.

Para utilizá-lo, precisamos importar:

```java
import java.util.Scanner;
```

Depois criamos o objeto:

```java
Scanner scanner = new Scanner(System.in);
```

O `System.in` indica que os dados serão recebidos pelo teclado.

---

# 3. Recebendo dados pelo terminal

Vamos começar pedindo o nome do aluno.

```java
System.out.print("Digite o nome do aluno: ");
String nome = scanner.nextLine();
```

Quando o usuário digitar o nome e pressionar Enter, o valor ficará armazenado na variável:

```java
nome
```

Agora fazemos a mesma coisa para a turma:

```java
System.out.print("Digite a turma: ");
String turma = scanner.nextLine();
```

E também para o e-mail:

```java
System.out.print("Digite o e-mail: ");
String email = scanner.nextLine();
```

Neste momento nós já temos três variáveis:

```java
String nome;
String turma;
String email;
```

Essas informações vieram diretamente do teclado.

---

# 4. Criando o comando SQL

Agora vamos criar nosso comando `INSERT`.

```java
String sql = """
        INSERT INTO aluno
        (nome, turma, email)
        VALUES (?, ?, ?)
        """;
```

Os símbolos `?` representam os valores que serão enviados posteriormente.

Podemos visualizar assim:

```text
nome  → primeiro ?
turma → segundo ?
email → terceiro ?
```

---

# 5. Criando o PreparedStatement

Depois de criar o SQL, nós criamos o `PreparedStatement`.

```java
PreparedStatement stmt =
        conexao.prepareStatement(sql);
```

Agora precisamos informar quais valores substituirão os símbolos `?`.

```java
stmt.setString(1, nome);
stmt.setString(2, turma);
stmt.setString(3, email);
```

Observe:

```text
1 → nome
2 → turma
3 → email
```

O primeiro número representa a posição do `?` dentro do SQL.

---

# 6. Executando o INSERT

Para executar o comando usamos:

```java
stmt.executeUpdate();
```

O método `executeUpdate()` é utilizado principalmente para comandos como:

```sql
INSERT
UPDATE
DELETE
```

Ele também pode retornar a quantidade de registros afetados.

Exemplo:

```java
int linhas = stmt.executeUpdate();
```

Depois podemos verificar:

```java
if (linhas > 0) {
    System.out.println("Aluno cadastrado com sucesso!");
}
```

---

# 7. Código completo

Agora vamos criar uma classe chamada:

```text
CadastrarAluno
```

Código completo:

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CadastrarAluno {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println("     CADASTRO DE ALUNO");
        System.out.println("==============================");

        System.out.print("Digite o nome do aluno: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a turma: ");
        String turma = scanner.nextLine();

        System.out.print("Digite o e-mail: ");
        String email = scanner.nextLine();

        String sql = """
                INSERT INTO aluno
                (nome, turma, email)
                VALUES (?, ?, ?)
                """;

        try {

            Connection conexao = Conexao.conectar();

            PreparedStatement stmt =
                    conexao.prepareStatement(sql);

            stmt.setString(1, nome);
            stmt.setString(2, turma);
            stmt.setString(3, email);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println();
                System.out.println("Aluno cadastrado com sucesso!");
            }

            stmt.close();
            conexao.close();

        } catch (SQLException erro) {

            System.out.println();
            System.out.println("Erro ao cadastrar aluno.");
            System.out.println(erro.getMessage());

        }

        scanner.close();
    }
}
```

---

# 8. Entendendo o código

Vamos analisar as partes principais.

## Scanner

```java
Scanner scanner = new Scanner(System.in);
```

Criamos o objeto responsável por receber os dados digitados no terminal.

## Recebendo o nome

```java
System.out.print("Digite o nome do aluno: ");
String nome = scanner.nextLine();
```

O usuário digita o nome e o Java guarda o conteúdo na variável `nome`.

## Recebendo a turma

```java
System.out.print("Digite a turma: ");
String turma = scanner.nextLine();
```

O valor digitado será armazenado na variável `turma`.

## Recebendo o e-mail

```java
System.out.print("Digite o e-mail: ");
String email = scanner.nextLine();
```

O e-mail será armazenado na variável `email`.

---

# 9. Entendendo os pontos de interrogação

Nosso comando SQL possui:

```java
VALUES (?, ?, ?)
```

Cada `?` receberá um valor.

```text
? número 1 → nome
? número 2 → turma
? número 3 → email
```

Por isso usamos:

```java
stmt.setString(1, nome);
stmt.setString(2, turma);
stmt.setString(3, email);
```

---

# 10. Por que utilizar PreparedStatement?

O `PreparedStatement` é recomendado quando os dados vêm do usuário.

Entre suas vantagens estão:

```text
Mais segurança
Redução do risco de SQL Injection
Código mais organizado
Separação entre SQL e valores
Facilidade para comandos repetitivos
```

Por exemplo, não precisamos montar uma String SQL desta forma:

```java
String sql =
    "INSERT INTO aluno(nome, turma, email) VALUES ('"
    + nome + "','" + turma + "','" + email + "')";
```

Essa abordagem é mais difícil de organizar e menos segura.

Com `PreparedStatement`, usamos:

```java
String sql =
    "INSERT INTO aluno(nome, turma, email) VALUES (?, ?, ?)";
```

E depois:

```java
stmt.setString(1, nome);
stmt.setString(2, turma);
stmt.setString(3, email);
```

---

# 11. Exemplo de execução

Ao executar o programa, o terminal poderá mostrar:

```text
==============================
     CADASTRO DE ALUNO
==============================

Digite o nome do aluno: Maria Silva
Digite a turma: Turma A
Digite o e-mail: maria@email.com

Aluno cadastrado com sucesso!
```

No PostgreSQL teremos algo semelhante a:

```text
id | nome        | turma   | email
1  | Maria Silva | Turma A | maria@email.com
```

---

# 12. Consultando o registro no PostgreSQL

Depois de cadastrar o aluno, podemos verificar os dados diretamente no PostgreSQL.

```sql
SELECT * FROM aluno;
```

O resultado deverá mostrar o registro inserido pelo programa Java.

---

# 13. Versão melhorada com try with resources

Existe uma forma melhor de trabalhar com recursos do banco de dados.

Podemos utilizar:

```text
try with resources
```

Essa estrutura fecha automaticamente a conexão e o `PreparedStatement` quando o bloco termina.

Código:

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CadastrarAluno {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==============================");
        System.out.println("     CADASTRO DE ALUNO");
        System.out.println("==============================");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Turma: ");
        String turma = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        String sql = """
                INSERT INTO aluno
                (nome, turma, email)
                VALUES (?, ?, ?)
                """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, nome);
            stmt.setString(2, turma);
            stmt.setString(3, email);

            stmt.executeUpdate();

            System.out.println();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException erro) {

            System.out.println();
            System.out.println("Erro ao cadastrar aluno.");
            System.out.println(erro.getMessage());

        }

        scanner.close();
    }
}
```

---

# 14. O que mudou?

Na primeira versão nós fechamos manualmente:

```java
stmt.close();
conexao.close();
```

Na segunda versão nós colocamos os recursos dentro do `try`.

```java
try (
    Connection conexao = Conexao.conectar();
    PreparedStatement stmt = conexao.prepareStatement(sql)
) {
```

Quando o bloco terminar, o Java fecha automaticamente:

```text
PreparedStatement
Connection
```

Isso ajuda a evitar conexões abertas desnecessariamente.

---

# 15. Fluxo completo

Agora podemos visualizar o funcionamento completo:

```text
Usuário
   ↓
Digita os dados
   ↓
Scanner
   ↓
Variáveis Java
   ↓
PreparedStatement
   ↓
INSERT
   ↓
PostgreSQL
```

Ou de forma ainda mais resumida:

```text
TECLADO
   ↓
JAVA
   ↓
JDBC
   ↓
POSTGRESQL
```

---

# 16. Exercício para os alunos

Agora vamos praticar.

Crie um programa chamado:

```text
CadastroProduto
```

Crie a tabela:

```sql
CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco NUMERIC(10,2) NOT NULL,
    quantidade INTEGER NOT NULL
);
```

O programa deverá solicitar:

```text
Nome do produto
Preço
Quantidade
```

Depois deverá cadastrar o produto no PostgreSQL utilizando:

```text
Scanner
Connection
PreparedStatement
try catch
```

---

# 17. Desafio

Depois de concluir o cadastro, vamos evoluir o programa.

Crie um menu:

```text
=========================
   SISTEMA DE ALUNOS
=========================

1 Cadastrar aluno
2 Listar alunos
3 Localizar aluno
4 Alterar aluno
5 Excluir aluno
0 Sair
```

Nosso objetivo será construir um pequeno CRUD utilizando:

```text
Scanner
PreparedStatement
ResultSet
PostgreSQL
```

CRUD significa:

```text
CREATE → cadastrar
READ   → consultar
UPDATE → alterar
DELETE → excluir
```

Esse exercício será importante porque a mesma lógica poderá ser utilizada posteriormente em uma interface Java Swing.

Em vez do usuário digitar pelo terminal, os dados virão de componentes como:

```text
JTextField
JComboBox
JButton
JTable
```

Mas o `PreparedStatement` continuará funcionando praticamente da mesma forma.

---

# 18. Resumo da aula

Nesta aula nós aprendemos:

```text
Como utilizar Scanner
Como receber dados pelo terminal
Como armazenar dados em variáveis
Como criar um INSERT
Como utilizar PreparedStatement
Como substituir os símbolos ?
Como utilizar executeUpdate
Como tratar SQLException
Como fechar recursos
Como utilizar try with resources
Como gravar informações no PostgreSQL
```

Agora nosso programa não trabalha mais apenas com valores definidos dentro do código.

O usuário pode digitar os dados diretamente no terminal e o Java grava essas informações no banco de dados.

Na próxima etapa, podemos utilizar essa mesma lógica para construir um CRUD completo e, depois, levar o cadastro para uma interface gráfica com Java Swing.
