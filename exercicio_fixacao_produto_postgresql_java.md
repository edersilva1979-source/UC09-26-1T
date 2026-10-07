# Exercício de Fixação

## Java com PostgreSQL, Scanner, PreparedStatement e ResultSet

## Objetivo

Neste exercício nós vamos praticar a conexão entre Java e PostgreSQL.

Vamos criar um pequeno sistema de cadastro de produtos utilizando o terminal do NetBeans.

O nosso programa deverá:

1. Conectar ao banco de dados PostgreSQL.
2. Pedir os dados de um produto pelo terminal.
3. Gravar o produto no banco de dados.
4. Consultar os produtos cadastrados.
5. Mostrar os produtos no terminal logo após o cadastro.

Vamos utilizar:

```text
PostgreSQL
NetBeans
JDBC
Scanner
Connection
PreparedStatement
ResultSet
```

# 1. Criando o banco de dados

Primeiro nós vamos abrir o PostgreSQL pelo pgAdmin.

Vamos criar um banco de dados chamado:

```text
BD_controle_261T
```


Depois de criar o banco, vamos abrir o Query Tool dentro dele.

# 2. Criando a tabela Produto

Agora vamos criar a tabela responsável por armazenar os produtos.

Nossa tabela terá:

```text
ID
Descricao
Estoque
Valor de Venda
```

Comando:

```sql
CREATE TABLE Produto (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    estoque INTEGER DEFAULT 0,
    valor_venda NUMERIC(10,2) CHECK (valor_venda >= 0)
);
```

## Entendendo os campos

O campo `id` será automático:

```sql
id SERIAL PRIMARY KEY
```

Por isso nós não vamos pedir o ID para o usuário.

A descrição não poderá ser nula:

```sql
descricao VARCHAR(100) NOT NULL
```

O estoque terá valor padrão igual a zero:

```sql
estoque INTEGER DEFAULT 0
```

O valor de venda não poderá ser negativo:

```sql
valor_venda NUMERIC(10,2) CHECK (valor_venda >= 0)
```

# 3. Estrutura do projeto no NetBeans

Neste exercício nós vamos criar somente duas classes.

```text
Projeto
│
└── Source Packages
    │
    ├── conexao
    │   └── conexao.java
    │
    └── view
        └── principal.java
```

A classe `conexao` será responsável pela conexão com o banco.

A classe `principal` será responsável pelo método `main`, pelo `Scanner`, pelo cadastro e pela consulta.

# 4. Adicionando o driver PostgreSQL

Antes de testar a conexão, nós precisamos adicionar o driver JDBC do PostgreSQL ao projeto.

No NetBeans:

```text
Projeto
Properties
Libraries
Classpath
Add JAR Folder
```

Depois selecionamos o arquivo do driver PostgreSQL.

# 5. Criando a classe conexao

Crie o pacote:

```text
conexao
```

Dentro dele crie a classe:

```text
conexao
```

Código:

```java
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {

    public static Connection conectar() {
.
.
.
.

```

A senha deverá ser alterada caso a senha do PostgreSQL do computador seja diferente de `root`.

# 6. Criando a classe principal

Agora crie o pacote:

```text
view
```

Dentro dele crie a classe:

```text
principal
```

Essa classe terá:

```java
public static void main(String[] args)
```

Nela nós vamos pedir os dados do produto, fazer o cadastro e depois mostrar os produtos cadastrados.

# 7. Dados solicitados

O programa deverá pedir:

```text
Descrição do produto
Quantidade em estoque
Valor de venda
```

O ID não será solicitado, pois será gerado pelo PostgreSQL.

# 8. Criando o Scanner

Vamos criar:

```java
Scanner scanner = new Scanner(System.in);
```

Para a descrição:

Para o estoque:

Para o valor de venda:


Assim nós conseguimos aceitar valores digitados com vírgula ou ponto.

# 9. Criando o INSERT

Vamos criar:

```java
.......
        INSERT INTO Produto
   .
.
.

```

Agora preenchemos os valores:

```java
stmt.setString....
.
.
.
```

Temos:

```text
1 corresponde à descrição
2 corresponde ao estoque
3 corresponde ao valor de venda
```

# 10. Executando o cadastro

Para executar o INSERT:


# 11. Criando o SELECT

Depois de salvar, nós vamos consultar os produtos:


# 12. Resultado esperado

Exemplo:

```text
==============================
     CADASTRO DE PRODUTOS
==============================

Digite a descrição do produto: Teclado USB
Digite a quantidade em estoque: 15
Digite o valor de venda: 89,90

Produto cadastrado com sucesso!

==============================
     PRODUTOS CADASTRADOS
==============================

ID: 1
Descrição: Teclado USB
Estoque: 15
Valor de venda: R$ 89.90
==============================

ID: 2
Descrição: NOTEBOOK
Estoque: 5
Valor de venda: R$ 3500.00
==============================
```

# 13. O que eu quero que vocês observem

Neste exercício nós utilizamos dois comandos SQL.

Para gravar:

```sql
INSERT
```

Executamos com:

```java
executeUpdate();
```

Para consultar:

```sql
SELECT
```

Executamos com:

```java
executeQuery();
```

O resultado da consulta é recebido por:

```java
ResultSet
```

# 14. Fluxo completo

```text
Início
  ↓
Scanner
  ↓
Digitar descrição
  ↓
Digitar estoque
  ↓
Digitar valor de venda
  ↓
Conectar ao PostgreSQL
  ↓
PreparedStatement
  ↓
INSERT
  ↓
Produto cadastrado
  ↓
SELECT
  ↓
ResultSet
  ↓
Mostrar produtos
  ↓
Fim
```

# 15. Regras obrigatórias do exercício

Para considerar o exercício concluído, eu vou verificar se vocês fizeram:

1. Banco `BD_controle_261T`.
2. Tabela `Produto`.
3. ID automático.
4. Descrição obrigatória.
5. Estoque com valor padrão zero.
6. Valor de venda sem aceitar números negativos.
7. Pacote `conexao`.
8. Classe `conexao`.
9. Pacote `view`.
10. Classe `principal`.
11. Uso de `Scanner`.
12. Uso de `Connection`.
13. Uso de `PreparedStatement`.
14. Comando `INSERT`.
15. Uso de `ResultSet`.
16. Comando `SELECT`.
17. Exibição dos produtos logo após o cadastro.

# 17. Testes

## Teste 1

Cadastre:

```text
Descrição: Monitor
Estoque: 10
Valor de venda: 899,90
```

O cadastro deverá funcionar.

## Teste 2

Cadastre:

```text
Descrição: Notebook
Estoque: 5
Valor de venda: 3500,00
```

Depois do cadastro, os dois produtos deverão aparecer no terminal.

## Teste 3

Tente inserir diretamente no PostgreSQL:

```sql
INSERT INTO Produto
(descricao, estoque, valor_venda)
VALUES
('Produto de Teste', 1, -50);
```

O PostgreSQL deverá impedir o cadastro por causa do:

```sql
CHECK (valor_venda >= 0)
```

## Teste 4

Tente inserir:

```sql
INSERT INTO Produto
(descricao, estoque, valor_venda)
VALUES
(NULL, 5, 100);
```

O PostgreSQL deverá impedir a gravação por causa do:

```sql
NOT NULL
```

# 18. Exercício 2

Depois de concluir o exercício principal, vamos melhorar o programa.

Antes do INSERT, verifique se a descrição ficou vazia:

```java
if (descricao.is....
.
.
.

```

Também podemos verificar o valor:

```java
if (valorVenda....
.
.
.
.
```

Assim nós teremos validação no Java e também no PostgreSQL.

# 19. Resumo

Neste exercício nós praticamos:

```text
Criação de banco no PostgreSQL
Criação de tabela
PRIMARY KEY
SERIAL
NOT NULL
DEFAULT
CHECK
JDBC
Connection
Scanner
PreparedStatement
INSERT
executeUpdate
SELECT
ResultSet
executeQuery
Tratamento de exceções
```

Agora nós já conseguimos receber informações pelo teclado, gravar essas informações no PostgreSQL e consultar os registros logo depois.

Esse conhecimento será utilizado posteriormente quando substituirmos o `Scanner` pelos componentes do Java Swing.
