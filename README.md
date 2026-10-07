<h1 align="center"> PostgreSQL para quem já conhece MySQL</h1>

<div align="center">
<img src="Logo_completo.png" width="300" alt="Exemplo">
</div>


Este repositório contém o material de apoio de 3 aulas sobre PostgreSQL, cada uma com duração aproximada de 3 horas.

O conteúdo foi preparado para alunos que já possuem conhecimento básico de MySQL e precisam conhecer o PostgreSQL, ao mesmo tempo em que fazem uma revisão dos principais comandos da linguagem SQL.

Ao longo das aulas, nós vamos trabalhar com exemplos simples, comandos práticos e exercícios que simulam situações de um sistema de vendas.

## Objetivos

Ao final das aulas, nós deveremos ser capazes de:

1. Entender o que é PostgreSQL

2. Identificar diferenças básicas entre PostgreSQL e MySQL

3. Criar bancos de dados e tabelas

4. Inserir dados

5. Consultar informações

6. Atualizar registros

7. Excluir registros

8. Trabalhar com filtros

9. Ordenar resultados

10. Criar relacionamentos entre tabelas

11. Utilizar JOIN

12. Utilizar funções de agregação

13. Trabalhar com GROUP BY e HAVING

14. Criar subconsultas

15. Criar views

16. Utilizar transações

17. Conhecer alguns recursos específicos do PostgreSQL

## Tecnologias utilizadas

Durante as aulas vamos utilizar:

```text
PostgreSQL
pgAdmin 4
SQL
```

## Banco utilizado nos exemplos

Durante as aulas vamos criar um pequeno sistema de vendas.

As principais tabelas serão:

```text
cliente
categoria
produto
pedido
item_pedido
```

Essas tabelas permitirão trabalhar com cadastro, consulta, relacionamento e relatórios.

## Aula 1

### Introdução ao PostgreSQL e revisão de SQL

Nesta aula vamos revisar os fundamentos da linguagem SQL e conhecer o ambiente do PostgreSQL.

Principais assuntos:

```text
PostgreSQL
pgAdmin
CREATE DATABASE
CREATE TABLE
PRIMARY KEY
IDENTITY
INSERT
SELECT
WHERE
AND
OR
IN
LIKE
ILIKE
ORDER BY
LIMIT
UPDATE
DELETE
```

Também vamos criar as primeiras tabelas do sistema.

Exemplo:

```sql
CREATE TABLE cliente (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(120),
    cidade VARCHAR(80),
    uf CHAR(2),
    ativo BOOLEAN DEFAULT TRUE
);
```

Inserindo dados:

```sql
INSERT INTO cliente
(nome, email, cidade, uf)
VALUES
('Ana Souza', 'ana@email.com', 'Porto Alegre', 'RS');
```

Consultando dados:

```sql
SELECT *
FROM cliente;
```

Filtrando:

```sql
SELECT *
FROM cliente
WHERE cidade = 'Porto Alegre';
```

## Aula 2

### Relacionamentos, JOIN e funções

Nesta aula vamos avançar para consultas utilizando mais de uma tabela.

Principais assuntos:

```text
PRIMARY KEY
FOREIGN KEY
INNER JOIN
LEFT JOIN
COUNT
SUM
AVG
MIN
MAX
GROUP BY
HAVING
DISTINCT
BETWEEN
IS NULL
COALESCE
CASE
```

Exemplo de JOIN:

```sql
SELECT
    p.nome AS produto,
    c.nome AS categoria
FROM produto p
INNER JOIN categoria c
ON p.categoria_id = c.id;
```

Exemplo com função:

```sql
SELECT
    c.nome AS categoria,
    COUNT(p.id) AS quantidade
FROM categoria c
LEFT JOIN produto p
ON p.categoria_id = c.id
GROUP BY c.nome;
```

## Aula 3

### SQL aplicado e recursos do PostgreSQL

Nesta aula vamos conhecer recursos que aparecem com frequência em projetos reais.

Principais assuntos:

```text
Subconsultas
Views
RETURNING
Funções de data
Funções de texto
ALTER TABLE
UNIQUE
CHECK
DEFAULT
INDEX
BEGIN
COMMIT
ROLLBACK
```

Exemplo de subconsulta:

```sql
SELECT *
FROM produto
WHERE preco > (
    SELECT AVG(preco)
    FROM produto
);
```

Exemplo de view:

```sql
CREATE VIEW vw_pedidos_clientes AS
SELECT
    p.id AS pedido,
    c.nome AS cliente,
    p.data_pedido,
    p.status
FROM pedido p
INNER JOIN cliente c
ON p.cliente_id = c.id;
```

Exemplo de transação:

```sql
BEGIN;

UPDATE produto
SET estoque = estoque - 1
WHERE id = 1;

COMMIT;
```

## Principais comandos revisados

```sql
CREATE DATABASE
CREATE TABLE
ALTER TABLE
INSERT
SELECT
UPDATE
DELETE
INNER JOIN
LEFT JOIN
GROUP BY
HAVING
ORDER BY
LIMIT
```

## Funções utilizadas

```sql
COUNT
SUM
AVG
MIN
MAX
UPPER
LOWER
LENGTH
COALESCE
CONCAT
```

## Comparação rápida entre MySQL e PostgreSQL

Quem já conhece MySQL perceberá que boa parte dos comandos SQL funciona de maneira muito parecida.

Por exemplo:

```sql
SELECT *
FROM cliente
WHERE cidade = 'Porto Alegre';
```

Esse comando é praticamente igual nos dois bancos.

Uma diferença comum está na geração automática de identificadores.

No PostgreSQL podemos utilizar:

```sql
id INTEGER GENERATED ALWAYS AS IDENTITY
```

No MySQL normalmente encontramos:

```sql
id INT AUTO_INCREMENT
```

Outra característica útil do PostgreSQL é o `ILIKE`.

```sql
SELECT *
FROM cliente
WHERE nome ILIKE '%ana%';
```

Ele permite realizar pesquisas sem diferenciar letras maiúsculas e minúsculas.

## Estrutura sugerida do repositório

```text
postgresql_aulas/
│
├── README.md
│
├── curso_postgresql_3_aulas.md
│
├── sql/
│   ├── aula01.sql
│   ├── aula02.sql
│   └── aula03.sql
│
└── exercicios/
    ├── exercicio_aula01.md
    ├── exercicio_aula02.md
    └── exercicio_final.md
```

## Como utilizar este material

Primeiro instale o PostgreSQL e o pgAdmin.

Depois crie o banco:

```sql
CREATE DATABASE curso_postgresql;
```

Abra o Query Tool do pgAdmin e execute os exemplos apresentados nas aulas.

Durante os exercícios, altere os dados, crie novos registros e teste novas consultas.

A ideia não é apenas copiar os comandos.

Nós devemos entender o que cada comando faz e observar o resultado no banco.

## Cuidados importantes

Sempre confira o `WHERE` antes de executar um `UPDATE`.

Exemplo:

```sql
UPDATE cliente
SET ativo = FALSE
WHERE id = 2;
```

Também confira o `WHERE` antes de executar um `DELETE`.

```sql
DELETE FROM cliente
WHERE id = 5;
```

Um comando como:

```sql
DELETE FROM cliente;
```

pode remover todos os registros da tabela.

Antes de excluir ou alterar dados, uma boa prática é testar primeiro o filtro com um `SELECT`.

```sql
SELECT *
FROM cliente
WHERE id = 5;
```

Depois de conferir o resultado, podemos executar a alteração.

## Exercícios

Durante as aulas serão realizados exercícios envolvendo:

```text
Cadastro de clientes
Cadastro de produtos
Cadastro de categorias
Pedidos
Itens de pedidos
Controle de estoque
Consultas
Filtros
Relatórios
JOIN
Agrupamentos
Views
Transações
```

## Desafio final

Ao final das três aulas, cada aluno deverá montar consultas capazes de responder perguntas como:

```text
Quais são os produtos mais caros?
Quais produtos estão sem estoque?
Qual é o preço médio dos produtos?
Quantos produtos existem em cada categoria?
Quais clientes possuem pedidos?
Qual é o valor total de cada pedido?
Quanto cada cliente já comprou?
Qual produto foi mais vendido?
Qual é o total vendido por produto?
```

## Continuação do conteúdo

Depois dessas aulas podemos utilizar esse mesmo banco para integração com Java.

Alguns conteúdos que podem ser estudados posteriormente:

```text
Java
NetBeans
PostgreSQL
JDBC
Connection
PreparedStatement
ResultSet
DAO
CRUD
Java Swing
```

Assim conseguimos aproveitar todo o banco criado durante as aulas para desenvolver um sistema Desktop completo.

## Material principal

O conteúdo detalhado das três aulas está disponível no arquivo:

```text
curso_postgresql_3_aulas.md
```

## Conclusão

PostgreSQL e MySQL possuem muitas características em comum.

Quem já conhece os principais comandos SQL terá facilidade para começar a trabalhar com PostgreSQL.

O mais importante é entender a lógica por trás dos comandos e praticar bastante.

SQL é aprendido principalmente fazendo consultas, testando filtros, relacionando tabelas e resolvendo problemas utilizando os dados.

Quanto mais praticarmos, mais natural será trabalhar com bancos de dados.
