# Curso Introdutório de PostgreSQL para quem já conhece MySQL

## Visão geral

Este material foi pensado para alunos que já tiveram contato com MySQL e agora irão conhecer o PostgreSQL.

Ao longo de 3 aulas, nós vamos revisar SQL e, ao mesmo tempo, aprender como trabalhar com PostgreSQL na prática.

A proposta é usar comandos simples, exemplos próximos de situações reais e bastante prática.

## Objetivos gerais

Ao final das 3 aulas, nós deveremos conseguir:

1. Entender o que é PostgreSQL e suas principais diferenças em relação ao MySQL.

2. Criar banco de dados e tabelas.

3. Inserir, consultar, alterar e excluir dados.

4. Trabalhar com filtros e ordenação.

5. Utilizar funções de agregação.

6. Relacionar tabelas utilizando JOIN.

7. Criar consultas com GROUP BY e HAVING.

8. Utilizar subconsultas.

9. Criar views.

10. Compreender chaves primárias, chaves estrangeiras e integridade dos dados.

11. Utilizar transações.

12. Aplicar SQL em pequenos problemas do dia a dia.

# Ambiente das aulas

Nós vamos utilizar:

1. PostgreSQL

2. pgAdmin 4

3. Editor SQL do pgAdmin

4. Um banco chamado curso_postgresql

# Banco utilizado nas aulas

Durante as aulas, nós vamos trabalhar com um pequeno sistema de vendas.

Teremos as seguintes entidades:

1. Clientes

2. Categorias

3. Produtos

4. Pedidos

5. Itens do pedido

Isso permitirá revisar praticamente os principais comandos SQL.

# AULA 1

## PostgreSQL, revisão de SQL e comandos básicos


## Objetivos da aula

Nesta aula nós vamos:

1. Conhecer o PostgreSQL.

2. Comparar PostgreSQL e MySQL.

3. Revisar o conceito de banco de dados relacional.

4. Criar banco de dados.

5. Criar tabelas.

6. Utilizar INSERT.

7. Utilizar SELECT.

8. Utilizar UPDATE.

9. Utilizar DELETE.

10. Trabalhar com WHERE, ORDER BY e LIMIT.

# 1. O que é PostgreSQL

PostgreSQL é um Sistema Gerenciador de Banco de Dados Relacional.

Também podemos encontrar a sigla SGBD.

Ele permite armazenar, organizar, consultar e proteger dados.

Exemplos de informações que poderiam ser armazenadas:

1. Clientes de uma loja

2. Produtos

3. Funcionários

4. Pedidos

5. Notas

6. Usuários de um sistema

7. Agendamentos

8. Estoque

# 2. PostgreSQL e MySQL

Quem já utilizou MySQL perceberá que grande parte do SQL é muito parecida.

Exemplo no MySQL:

```sql
SELECT nome
FROM cliente
WHERE cidade = 'Porto Alegre';
```

No PostgreSQL:

```sql
SELECT nome
FROM cliente
WHERE cidade = 'Porto Alegre';
```

O comando é praticamente igual.

Algumas diferenças aparecem em tipos de dados, geração automática de códigos, funções, administração do banco e recursos mais avançados.

# 3. SQL

SQL significa Structured Query Language.

É a linguagem utilizada para conversar com bancos de dados relacionais.

Nós podemos dividir os comandos mais utilizados em alguns grupos.

## DDL

Comandos utilizados para definir estruturas.

Exemplos:

```sql
CREATE
ALTER
DROP
```

## DML

Comandos utilizados para manipular dados.

Exemplos:

```sql
INSERT
UPDATE
DELETE
```

## Consulta

O comando mais conhecido é:

```sql
SELECT
```

# 4. Criando o banco

No PostgreSQL podemos executar:

```sql
CREATE DATABASE curso_postgresql;
```

Depois devemos selecionar o banco criado no pgAdmin.

# 5. Criando nossa primeira tabela

Vamos começar com clientes.

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

# 6. Entendendo a tabela

## id

```sql
id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY
```

O campo id será o identificador de cada cliente.

O PostgreSQL gerará o valor automaticamente.

## nome

```sql
nome VARCHAR(100) NOT NULL
```

Permite texto com até 100 caracteres.

NOT NULL significa que o valor é obrigatório.

## email

```sql
email VARCHAR(120)
```

Armazena o email.

## ativo

```sql
ativo BOOLEAN DEFAULT TRUE
```

BOOLEAN trabalha normalmente com:

```text
TRUE
FALSE
```

# 7. Inserindo dados com INSERT

```sql
INSERT INTO cliente
(nome, email, cidade, uf)
VALUES
('Ana Souza', 'ana@email.com', 'Porto Alegre', 'RS');
```

Outro cliente:

```sql
INSERT INTO cliente
(nome, email, cidade, uf)
VALUES
('Carlos Lima', 'carlos@email.com', 'Novo Hamburgo', 'RS');
```

Podemos inserir vários registros juntos.

```sql
INSERT INTO cliente
(nome, email, cidade, uf)
VALUES
('Mariana Silva', 'mariana@email.com', 'São Leopoldo', 'RS'),
('Paulo Martins', 'paulo@email.com', 'Canoas', 'RS'),
('Fernanda Alves', 'fernanda@email.com', 'Porto Alegre', 'RS');
```

# 8. SELECT

SELECT é utilizado para consultar informações.

Todos os campos:

```sql
SELECT *
FROM cliente;
```

Campos específicos:

```sql
SELECT nome, email
FROM cliente;
```

# 9. WHERE

WHERE cria filtros.

```sql
SELECT *
FROM cliente
WHERE cidade = 'Porto Alegre';
```

Outro exemplo:

```sql
SELECT *
FROM cliente
WHERE uf = 'RS';
```

# 10. Operadores de comparação

Igual:

```sql
=
```

Diferente:

```sql
<>
```

Maior:

```sql
>
```

Menor:

```sql
<
```

Maior ou igual:

```sql
>=
```

Menor ou igual:

```sql
<=
```

Exemplo:

```sql
SELECT *
FROM cliente
WHERE id >= 3;
```

# 11. AND

AND significa que todas as condições precisam ser verdadeiras.

```sql
SELECT *
FROM cliente
WHERE cidade = 'Porto Alegre'
AND ativo = TRUE;
```

# 12. OR

OR significa que uma das condições pode ser verdadeira.

```sql
SELECT *
FROM cliente
WHERE cidade = 'Porto Alegre'
OR cidade = 'Canoas';
```

# 13. IN

IN facilita filtros com vários valores.

```sql
SELECT *
FROM cliente
WHERE cidade IN ('Porto Alegre', 'Canoas', 'Novo Hamburgo');
```

# 14. LIKE

LIKE é usado para pesquisas de texto.

Clientes cujo nome começa com A:

```sql
SELECT *
FROM cliente
WHERE nome LIKE 'A%';
```

Clientes cujo nome termina com Silva:

```sql
SELECT *
FROM cliente
WHERE nome LIKE '%Silva';
```

Clientes que possuem a palavra Silva em qualquer posição:

```sql
SELECT *
FROM cliente
WHERE nome LIKE '%Silva%';
```

# 15. ILIKE no PostgreSQL

No PostgreSQL temos o ILIKE.

Ele faz pesquisas ignorando diferenças entre letras maiúsculas e minúsculas.

```sql
SELECT *
FROM cliente
WHERE nome ILIKE '%ana%';
```

Essa é uma diferença bastante útil em relação ao comportamento tradicional encontrado em muitos ambientes MySQL.

# 16. ORDER BY

Ordenação crescente:

```sql
SELECT *
FROM cliente
ORDER BY nome ASC;
```

Ordenação decrescente:

```sql
SELECT *
FROM cliente
ORDER BY nome DESC;
```

# 17. LIMIT

Podemos limitar a quantidade de resultados.

```sql
SELECT *
FROM cliente
LIMIT 3;
```

# 18. UPDATE

UPDATE altera informações existentes.

```sql
UPDATE cliente
SET cidade = 'Esteio'
WHERE id = 2;
```

Alterando mais de um campo:

```sql
UPDATE cliente
SET cidade = 'Sapucaia do Sul',
    uf = 'RS'
WHERE id = 3;
```

## Atenção

Nunca devemos executar um UPDATE sem analisar o WHERE.

Exemplo perigoso:

```sql
UPDATE cliente
SET ativo = FALSE;
```

Esse comando altera todos os clientes.

# 19. DELETE

DELETE remove registros.

```sql
DELETE FROM cliente
WHERE id = 5;
```

Também precisamos tomar cuidado com o WHERE.

Este comando:

```sql
DELETE FROM cliente;
```

remove todos os registros da tabela.

# 20. Exercício guiado

Vamos criar uma tabela chamada categoria.

```sql
CREATE TABLE categoria (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(80) NOT NULL
);
```

Inserindo categorias:

```sql
INSERT INTO categoria
(nome)
VALUES
('Informática'),
('Escritório'),
('Eletrônicos'),
('Acessórios');
```

Consultando:

```sql
SELECT *
FROM categoria;
```

# 21. Criando produtos

```sql
CREATE TABLE produto (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(200),
    preco NUMERIC(10,2) NOT NULL,
    estoque INTEGER DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE,
    categoria_id INTEGER,
    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES categoria(id)
);
```

# 22. Inserindo produtos

```sql
INSERT INTO produto
(nome, descricao, preco, estoque, categoria_id)
VALUES
('Mouse USB', 'Mouse com conexão USB', 45.90, 20, 4),
('Teclado USB', 'Teclado padrão ABNT2', 89.90, 15, 4),
('Monitor 24', 'Monitor LED de 24 polegadas', 899.90, 8, 3),
('Notebook', 'Notebook para uso profissional', 3499.90, 5, 1),
('Cadeira de Escritório', 'Cadeira com ajuste de altura', 699.90, 4, 2);
```

# 23. Consultas práticas

Produtos acima de 500 reais:

```sql
SELECT *
FROM produto
WHERE preco > 500;
```

Produtos com estoque menor que 10:

```sql
SELECT *
FROM produto
WHERE estoque < 10;
```

Produtos ativos:

```sql
SELECT *
FROM produto
WHERE ativo = TRUE;
```

Produtos ordenados pelo preço:

```sql
SELECT *
FROM produto
ORDER BY preco ASC;
```

# 24. Desafio da aula 1

Crie mais 5 clientes e 5 produtos.

Depois execute consultas para:

1. Mostrar todos os clientes.

2. Mostrar clientes de Porto Alegre.

3. Mostrar clientes ordenados pelo nome.

4. Mostrar produtos acima de 100 reais.

5. Mostrar produtos com estoque menor que 10.

6. Alterar o preço de um produto.

7. Alterar a cidade de um cliente.

8. Desativar um cliente.

9. Excluir um produto.

10. Mostrar os 3 produtos mais caros.

# Encerramento da aula 1

Nesta primeira aula revisamos:

```text
CREATE DATABASE
CREATE TABLE
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

# AULA 2

## Relacionamentos, JOIN, funções e agrupamentos

Duração: 3 horas

## Objetivos da aula

Nesta aula nós vamos:

1. Revisar chave primária.

2. Revisar chave estrangeira.

3. Entender relacionamentos.

4. Utilizar INNER JOIN.

5. Utilizar LEFT JOIN.

6. Utilizar funções de agregação.

7. Utilizar GROUP BY.

8. Utilizar HAVING.

9. Trabalhar com alias.

10. Criar consultas mais completas.

# 1. Chave primária

Uma chave primária identifica um registro de maneira única.

Exemplo:

```sql
id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY
```

Cada cliente possui um id diferente.

# 2. Chave estrangeira

Uma chave estrangeira relaciona uma tabela com outra.

Na tabela produto temos:

```sql
categoria_id INTEGER
```

E depois:

```sql
FOREIGN KEY (categoria_id)
REFERENCES categoria(id)
```

Isso significa que categoria_id aponta para um registro da tabela categoria.

# 3. JOIN

JOIN permite combinar informações de tabelas diferentes.

Temos:

```text
produto
```

e:

```text
categoria
```

O produto guarda apenas categoria_id.

Com JOIN podemos mostrar o nome da categoria junto com o produto.

# 4. INNER JOIN

```sql
SELECT
    produto.id,
    produto.nome,
    produto.preco,
    categoria.nome AS categoria
FROM produto
INNER JOIN categoria
ON produto.categoria_id = categoria.id;
```

# 5. Alias

Alias cria nomes temporários.

Em vez de:

```sql
produto.nome
```

podemos utilizar:

```sql
p.nome
```

Exemplo:

```sql
SELECT
    p.id,
    p.nome,
    p.preco,
    c.nome AS categoria
FROM produto p
INNER JOIN categoria c
ON p.categoria_id = c.id;
```

Esse formato é muito utilizado em projetos reais.

# 6. LEFT JOIN

LEFT JOIN mostra todos os registros da tabela da esquerda, mesmo quando não existe correspondência na tabela da direita.

```sql
SELECT
    p.nome AS produto,
    c.nome AS categoria
FROM produto p
LEFT JOIN categoria c
ON p.categoria_id = c.id;
```

# 7. Criando pedidos

```sql
CREATE TABLE pedido (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    data_pedido DATE DEFAULT CURRENT_DATE,
    status VARCHAR(30) DEFAULT 'ABERTO',
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
);
```

# 8. Criando itens do pedido

```sql
CREATE TABLE item_pedido (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pedido_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_unitario NUMERIC(10,2) NOT NULL,
    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedido(id),
    CONSTRAINT fk_item_produto
        FOREIGN KEY (produto_id)
        REFERENCES produto(id)
);
```

# 9. Inserindo pedidos

```sql
INSERT INTO pedido
(cliente_id, status)
VALUES
(1, 'FINALIZADO'),
(2, 'FINALIZADO'),
(1, 'ABERTO');
```

# 10. Inserindo itens

```sql
INSERT INTO item_pedido
(pedido_id, produto_id, quantidade, valor_unitario)
VALUES
(1, 1, 2, 45.90),
(1, 2, 1, 89.90),
(2, 3, 1, 899.90),
(3, 1, 1, 45.90);
```

# 11. JOIN entre pedido e cliente

```sql
SELECT
    p.id AS pedido,
    c.nome AS cliente,
    p.data_pedido,
    p.status
FROM pedido p
INNER JOIN cliente c
ON p.cliente_id = c.id;
```

# 12. JOIN com três tabelas

```sql
SELECT
    ped.id AS pedido,
    cli.nome AS cliente,
    pro.nome AS produto,
    item.quantidade,
    item.valor_unitario
FROM item_pedido item
INNER JOIN pedido ped
ON item.pedido_id = ped.id
INNER JOIN cliente cli
ON ped.cliente_id = cli.id
INNER JOIN produto pro
ON item.produto_id = pro.id;
```

# 13. Calculando subtotal

```sql
SELECT
    pro.nome,
    item.quantidade,
    item.valor_unitario,
    item.quantidade * item.valor_unitario AS subtotal
FROM item_pedido item
INNER JOIN produto pro
ON item.produto_id = pro.id;
```

# 14. Função COUNT

COUNT conta registros.

```sql
SELECT COUNT(*)
FROM cliente;
```

Podemos criar um nome para o resultado:

```sql
SELECT COUNT(*) AS total_clientes
FROM cliente;
```

# 15. SUM

SUM soma valores.

```sql
SELECT SUM(estoque)
FROM produto;
```

Total financeiro do estoque:

```sql
SELECT SUM(preco * estoque) AS valor_estoque
FROM produto;
```

# 16. AVG

AVG calcula média.

```sql
SELECT AVG(preco) AS preco_medio
FROM produto;
```

# 17. MIN

```sql
SELECT MIN(preco) AS menor_preco
FROM produto;
```

# 18. MAX

```sql
SELECT MAX(preco) AS maior_preco
FROM produto;
```

# 19. GROUP BY

GROUP BY agrupa registros.

Exemplo: quantidade de produtos por categoria.

```sql
SELECT
    c.nome AS categoria,
    COUNT(p.id) AS quantidade
FROM categoria c
LEFT JOIN produto p
ON p.categoria_id = c.id
GROUP BY c.nome;
```

# 20. Total vendido por pedido

```sql
SELECT
    pedido_id,
    SUM(quantidade * valor_unitario) AS total
FROM item_pedido
GROUP BY pedido_id;
```

# 21. Total comprado por cliente

```sql
SELECT
    c.nome AS cliente,
    SUM(i.quantidade * i.valor_unitario) AS total_comprado
FROM cliente c
INNER JOIN pedido p
ON p.cliente_id = c.id
INNER JOIN item_pedido i
ON i.pedido_id = p.id
GROUP BY c.nome;
```

# 22. HAVING

HAVING filtra resultados depois do agrupamento.

Clientes que compraram mais de 100 reais:

```sql
SELECT
    c.nome AS cliente,
    SUM(i.quantidade * i.valor_unitario) AS total_comprado
FROM cliente c
INNER JOIN pedido p
ON p.cliente_id = c.id
INNER JOIN item_pedido i
ON i.pedido_id = p.id
GROUP BY c.nome
HAVING SUM(i.quantidade * i.valor_unitario) > 100;
```

# 23. WHERE e HAVING

WHERE filtra registros antes do agrupamento.

HAVING filtra resultados depois do agrupamento.

Exemplo:

```sql
SELECT
    c.nome AS cliente,
    SUM(i.quantidade * i.valor_unitario) AS total_comprado
FROM cliente c
INNER JOIN pedido p
ON p.cliente_id = c.id
INNER JOIN item_pedido i
ON i.pedido_id = p.id
WHERE p.status = 'FINALIZADO'
GROUP BY c.nome
HAVING SUM(i.quantidade * i.valor_unitario) > 100;
```

# 24. DISTINCT

DISTINCT elimina valores repetidos.

```sql
SELECT DISTINCT cidade
FROM cliente;
```

# 25. BETWEEN

BETWEEN permite trabalhar com intervalos.

```sql
SELECT *
FROM produto
WHERE preco BETWEEN 100 AND 1000;
```

# 26. IS NULL

```sql
SELECT *
FROM cliente
WHERE email IS NULL;
```

# 27. IS NOT NULL

```sql
SELECT *
FROM cliente
WHERE email IS NOT NULL;
```

# 28. COALESCE

COALESCE permite substituir valores nulos.

```sql
SELECT
    nome,
    COALESCE(email, 'Email não informado') AS email
FROM cliente;
```

# 29. CASE

CASE permite criar condições dentro da consulta.

```sql
SELECT
    nome,
    preco,
    CASE
        WHEN preco < 100 THEN 'Baixo valor'
        WHEN preco <= 1000 THEN 'Valor médio'
        ELSE 'Alto valor'
    END AS faixa_preco
FROM produto;
```

# 30. Exercício da aula 2

Crie consultas para responder:

1. Qual é o total de clientes cadastrados?

2. Qual é o preço médio dos produtos?

3. Qual é o produto mais caro?

4. Qual é o produto mais barato?

5. Quantos produtos existem em cada categoria?

6. Quais clientes possuem pedidos?

7. Quais clientes não possuem pedidos?

8. Qual é o total de cada pedido?

9. Quanto cada cliente já comprou?

10. Quais clientes compraram mais de 500 reais?

11. Quais produtos possuem preço entre 100 e 1000 reais?

12. Quantas cidades diferentes existem no cadastro de clientes?

# Desafio da aula 2

Monte uma consulta que mostre:

```text
Número do pedido
Cliente
Produto
Quantidade
Valor unitário
Subtotal
Status do pedido
Data do pedido
```

Uma possível solução:

```sql
SELECT
    ped.id AS numero_pedido,
    cli.nome AS cliente,
    pro.nome AS produto,
    item.quantidade,
    item.valor_unitario,
    item.quantidade * item.valor_unitario AS subtotal,
    ped.status,
    ped.data_pedido
FROM item_pedido item
INNER JOIN pedido ped
ON item.pedido_id = ped.id
INNER JOIN cliente cli
ON ped.cliente_id = cli.id
INNER JOIN produto pro
ON item.produto_id = pro.id
ORDER BY ped.id;
```

# Encerramento da aula 2

Nesta aula nós trabalhamos principalmente com:

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

# AULA 3

## SQL aplicado, subconsultas, views, transações e recursos do PostgreSQL

Duração: 3 horas

## Objetivos da aula

Nesta aula nós vamos:

1. Trabalhar com subconsultas.

2. Criar views.

3. Entender transações.

4. Utilizar RETURNING.

5. Trabalhar com datas.

6. Utilizar funções de texto.

7. Utilizar ALTER TABLE.

8. Utilizar constraints.

9. Criar índices básicos.

10. Resolver um exercício integrador.

# 1. Subconsulta

Uma subconsulta é uma consulta dentro de outra consulta.

Exemplo: produtos com preço acima da média.

Primeiro podemos descobrir a média:

```sql
SELECT AVG(preco)
FROM produto;
```

Depois:

```sql
SELECT *
FROM produto
WHERE preco > (
    SELECT AVG(preco)
    FROM produto
);
```

# 2. Produto mais caro

```sql
SELECT *
FROM produto
WHERE preco = (
    SELECT MAX(preco)
    FROM produto
);
```

# 3. Clientes que possuem pedidos

```sql
SELECT *
FROM cliente
WHERE id IN (
    SELECT cliente_id
    FROM pedido
);
```

# 4. Clientes que não possuem pedidos

```sql
SELECT *
FROM cliente
WHERE id NOT IN (
    SELECT cliente_id
    FROM pedido
);
```

Também podemos resolver esse problema com LEFT JOIN.

```sql
SELECT
    c.*
FROM cliente c
LEFT JOIN pedido p
ON p.cliente_id = c.id
WHERE p.id IS NULL;
```

# 5. VIEW

Uma view funciona como uma consulta salva no banco.

Vamos criar uma visão dos pedidos.

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

Depois podemos consultar:

```sql
SELECT *
FROM vw_pedidos_clientes;
```

# 6. View de faturamento

```sql
CREATE VIEW vw_total_pedido AS
SELECT
    p.id AS pedido,
    c.nome AS cliente,
    SUM(i.quantidade * i.valor_unitario) AS total
FROM pedido p
INNER JOIN cliente c
ON p.cliente_id = c.id
INNER JOIN item_pedido i
ON i.pedido_id = p.id
GROUP BY p.id, c.nome;
```

Consulta:

```sql
SELECT *
FROM vw_total_pedido;
```

# 7. RETURNING no PostgreSQL

PostgreSQL permite retornar informações logo após INSERT, UPDATE ou DELETE.

Exemplo:

```sql
INSERT INTO categoria
(nome)
VALUES
('Games')
RETURNING id, nome;
```

Esse recurso é muito útil quando o sistema precisa saber imediatamente qual id foi gerado.

# 8. RETURNING com UPDATE

```sql
UPDATE produto
SET preco = 49.90
WHERE id = 1
RETURNING id, nome, preco;
```

# 9. RETURNING com DELETE

```sql
DELETE FROM categoria
WHERE id = 5
RETURNING *;
```

# 10. Trabalhando com datas

Data atual:

```sql
SELECT CURRENT_DATE;
```

Data e hora atuais:

```sql
SELECT CURRENT_TIMESTAMP;
```

# 11. Extraindo partes de uma data

```sql
SELECT
    EXTRACT(DAY FROM CURRENT_DATE) AS dia,
    EXTRACT(MONTH FROM CURRENT_DATE) AS mes,
    EXTRACT(YEAR FROM CURRENT_DATE) AS ano;
```

# 12. Intervalos de tempo

```sql
SELECT CURRENT_DATE + INTERVAL '30 days';
```

Outro exemplo:

```sql
SELECT CURRENT_TIMESTAMP + INTERVAL '2 hours';
```

# 13. Funções de texto

Transformar em maiúsculas:

```sql
SELECT UPPER(nome)
FROM cliente;
```

Transformar em minúsculas:

```sql
SELECT LOWER(nome)
FROM cliente;
```

Quantidade de caracteres:

```sql
SELECT
    nome,
    LENGTH(nome)
FROM cliente;
```

# 14. Concatenando informações

No PostgreSQL podemos utilizar:

```sql
||
```

Exemplo:

```sql
SELECT
    nome || ' | ' || cidade AS cliente
FROM cliente;
```

Também podemos utilizar CONCAT.

```sql
SELECT
    CONCAT(nome, ' | ', cidade)
FROM cliente;
```

# 15. ALTER TABLE

ALTER TABLE altera a estrutura de uma tabela.

Adicionar uma coluna:

```sql
ALTER TABLE cliente
ADD COLUMN telefone VARCHAR(20);
```

# 16. Renomeando coluna

```sql
ALTER TABLE cliente
RENAME COLUMN telefone TO celular;
```

# 17. Alterando tipo

Exemplo:

```sql
ALTER TABLE cliente
ALTER COLUMN celular TYPE VARCHAR(30);
```

# 18. Removendo coluna

```sql
ALTER TABLE cliente
DROP COLUMN celular;
```

# 19. UNIQUE

UNIQUE impede valores duplicados.

Podemos adicionar uma regra ao email:

```sql
ALTER TABLE cliente
ADD CONSTRAINT uk_cliente_email
UNIQUE (email);
```

Agora dois clientes não poderão possuir o mesmo email.

# 20. CHECK

CHECK permite criar uma regra.

Exemplo:

```sql
ALTER TABLE produto
ADD CONSTRAINT ck_produto_preco
CHECK (preco >= 0);
```

Assim o banco impede preços negativos.

# 21. NOT NULL

Podemos exigir um campo obrigatório.

Exemplo:

```sql
ALTER TABLE cliente
ALTER COLUMN cidade SET NOT NULL;
```

# 22. DEFAULT

Podemos definir valor padrão.

```sql
ALTER TABLE produto
ALTER COLUMN estoque SET DEFAULT 0;
```

# 23. Índice

Índices podem ajudar o banco a localizar informações mais rapidamente.

Exemplo:

```sql
CREATE INDEX idx_cliente_nome
ON cliente(nome);
```

Outro exemplo:

```sql
CREATE INDEX idx_produto_nome
ON produto(nome);
```

Não devemos criar índices em todas as colunas sem necessidade.

Índices também possuem custo de manutenção em INSERT, UPDATE e DELETE.

# 24. Transações

Uma transação permite executar vários comandos como uma única operação lógica.

Exemplo:

```sql
BEGIN;
```

Depois:

```sql
UPDATE produto
SET estoque = estoque - 1
WHERE id = 1;
```

Podemos confirmar:

```sql
COMMIT;
```

Ou cancelar:

```sql
ROLLBACK;
```

# 25. Exemplo de transação

Vamos imaginar uma venda.

Primeiro:

```sql
BEGIN;
```

Depois criamos o pedido:

```sql
INSERT INTO pedido
(cliente_id, status)
VALUES
(1, 'FINALIZADO');
```

Depois baixamos o estoque:

```sql
UPDATE produto
SET estoque = estoque - 1
WHERE id = 1;
```

Se tudo estiver certo:

```sql
COMMIT;
```

Se houver problema:

```sql
ROLLBACK;
```

# 26. DELETE com relacionamento

Se um cliente possuir pedidos, o PostgreSQL pode impedir sua exclusão por causa da chave estrangeira.

Exemplo:

```sql
DELETE FROM cliente
WHERE id = 1;
```

Se o cliente possuir pedidos, poderemos receber um erro.

Isso acontece para proteger a integridade do banco.

# 27. ON DELETE

Também podemos configurar comportamentos específicos.

Exemplo conceitual:

```sql
FOREIGN KEY (cliente_id)
REFERENCES cliente(id)
ON DELETE CASCADE
```

Com CASCADE, ao excluir o registro principal, registros relacionados também podem ser excluídos.

Esse recurso deve ser utilizado com cuidado.

# 28. Comparação rápida entre MySQL e PostgreSQL

## Campo automático no PostgreSQL

Forma recomendada:

```sql
id INTEGER GENERATED ALWAYS AS IDENTITY
```

No MySQL normalmente encontramos:

```sql
id INT AUTO_INCREMENT
```

## Pesquisa ignorando maiúsculas e minúsculas

PostgreSQL:

```sql
ILIKE
```

## Boolean

PostgreSQL trabalha muito bem com:

```sql
TRUE
FALSE
```

## Retorno após comandos

PostgreSQL:

```sql
RETURNING
```

Exemplo:

```sql
INSERT INTO categoria
(nome)
VALUES
('Redes')
RETURNING id;
```

# 29. Exercício integrador

Vamos imaginar que uma pequena loja pediu um relatório.

O relatório precisa mostrar:

1. Nome do cliente.

2. Quantidade de pedidos.

3. Total comprado.

4. Maior compra.

5. Média de valor dos pedidos.

6. Somente clientes que possuem pedidos finalizados.

# 30. Primeira consulta

Vamos calcular o valor dos pedidos.

```sql
SELECT
    p.id,
    p.cliente_id,
    SUM(i.quantidade * i.valor_unitario) AS total_pedido
FROM pedido p
INNER JOIN item_pedido i
ON i.pedido_id = p.id
WHERE p.status = 'FINALIZADO'
GROUP BY p.id, p.cliente_id;
```

# 31. Criando uma view auxiliar

```sql
CREATE VIEW vw_pedidos_finalizados AS
SELECT
    p.id AS pedido_id,
    p.cliente_id,
    SUM(i.quantidade * i.valor_unitario) AS total_pedido
FROM pedido p
INNER JOIN item_pedido i
ON i.pedido_id = p.id
WHERE p.status = 'FINALIZADO'
GROUP BY p.id, p.cliente_id;
```

# 32. Relatório final

```sql
SELECT
    c.nome AS cliente,
    COUNT(v.pedido_id) AS quantidade_pedidos,
    SUM(v.total_pedido) AS total_comprado,
    MAX(v.total_pedido) AS maior_compra,
    AVG(v.total_pedido) AS media_pedidos
FROM cliente c
INNER JOIN vw_pedidos_finalizados v
ON v.cliente_id = c.id
GROUP BY c.nome
ORDER BY total_comprado DESC;
```

# 33. Desafio final

Os alunos deverão montar um relatório contendo:

1. Código do produto.

2. Nome do produto.

3. Categoria.

4. Preço.

5. Estoque atual.

6. Quantidade vendida.

7. Total vendido em reais.

8. Situação do estoque.

A situação deverá seguir:

```text
Sem estoque
Estoque baixo
Estoque normal
```

Sugestão de regra:

```text
0 = Sem estoque
1 até 5 = Estoque baixo
Acima de 5 = Estoque normal
```

Uma possível solução:

```sql
SELECT
    p.id,
    p.nome AS produto,
    c.nome AS categoria,
    p.preco,
    p.estoque,
    COALESCE(SUM(i.quantidade), 0) AS quantidade_vendida,
    COALESCE(SUM(i.quantidade * i.valor_unitario), 0) AS total_vendido,
    CASE
        WHEN p.estoque = 0 THEN 'Sem estoque'
        WHEN p.estoque <= 5 THEN 'Estoque baixo'
        ELSE 'Estoque normal'
    END AS situacao_estoque
FROM produto p
INNER JOIN categoria c
ON p.categoria_id = c.id
LEFT JOIN item_pedido i
ON i.produto_id = p.id
GROUP BY
    p.id,
    p.nome,
    c.nome,
    p.preco,
    p.estoque
ORDER BY p.nome;
```

# 34. Exercício final individual

Cada aluno deverá acrescentar ao banco:

1. No mínimo 10 clientes.

2. No mínimo 5 categorias.

3. No mínimo 15 produtos.

4. No mínimo 8 pedidos.

5. No mínimo 15 itens de pedido.

Depois deverá criar consultas para responder:

1. Quais são os 5 produtos mais caros?

2. Quais produtos estão sem estoque?

3. Qual é a média de preço dos produtos?

4. Quantos produtos existem em cada categoria?

5. Quais clientes são de uma determinada cidade?

6. Quais clientes possuem pedidos?

7. Quais clientes ainda não realizaram pedidos?

8. Qual é o valor total de cada pedido?

9. Qual cliente mais gastou em valores absolutos?

10. Quais pedidos estão finalizados?

11. Quantos pedidos cada cliente possui?

12. Quais produtos foram vendidos?

13. Qual quantidade total foi vendida de cada produto?

14. Qual é o valor total vendido por produto?

15. Crie uma view contendo o resumo das vendas.

# Revisão geral das 3 aulas

Ao final destas aulas nós revisamos e praticamos os principais elementos de SQL.

## Estrutura do banco

```text
CREATE DATABASE
CREATE TABLE
ALTER TABLE
DROP
```

## Inserção

```text
INSERT
```

## Consulta

```text
SELECT
WHERE
AND
OR
IN
BETWEEN
LIKE
ILIKE
DISTINCT
ORDER BY
LIMIT
IS NULL
IS NOT NULL
```

## Alteração

```text
UPDATE
```

## Exclusão

```text
DELETE
```

## Relacionamentos

```text
PRIMARY KEY
FOREIGN KEY
INNER JOIN
LEFT JOIN
```

## Funções

```text
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

## Agrupamentos

```text
GROUP BY
HAVING
```

## Recursos adicionais

```text
CASE
VIEW
SUBQUERY
RETURNING
TRANSACTION
COMMIT
ROLLBACK
INDEX
UNIQUE
CHECK
DEFAULT
```

# Roteiro sugerido de tempo

## Aula 1

Primeira hora:

1. PostgreSQL

2. Comparação com MySQL

3. SQL

4. Banco

5. Tabelas

Segunda hora:

1. INSERT

2. SELECT

3. WHERE

4. LIKE

5. ILIKE

6. ORDER BY

7. LIMIT

Terceira hora:

1. UPDATE

2. DELETE

3. Produtos e categorias

4. Exercícios

## Aula 2

Primeira hora:

1. Chaves

2. Relacionamentos

3. INNER JOIN

4. LEFT JOIN

Segunda hora:

1. COUNT

2. SUM

3. AVG

4. MIN

5. MAX

6. GROUP BY

Terceira hora:

1. HAVING

2. CASE

3. COALESCE

4. Consultas com várias tabelas

5. Exercícios

## Aula 3

Primeira hora:

1. Subconsultas

2. Views

3. RETURNING

4. Datas

Segunda hora:

1. ALTER TABLE

2. Constraints

3. Índices

4. Transações

Terceira hora:

1. Exercício integrador

2. Relatório final

3. Revisão

4. Desafio individual

# Boas práticas para os alunos

1. Sempre leia o comando antes de executar.

2. Tenha cuidado com UPDATE sem WHERE.

3. Tenha cuidado com DELETE sem WHERE.

4. Utilize nomes claros em tabelas e campos.

5. Defina uma chave primária.

6. Use chaves estrangeiras quando houver relacionamento.

7. Evite dados duplicados.

8. Utilize NOT NULL quando a informação for obrigatória.

9. Utilize UNIQUE quando um valor não puder se repetir.

10. Teste consultas antes de utilizar os mesmos filtros em UPDATE ou DELETE.

Por exemplo, antes de executar:

```sql
DELETE FROM cliente
WHERE id = 10;
```

execute primeiro:

```sql
SELECT *
FROM cliente
WHERE id = 10;
```

Assim conseguimos verificar qual registro será afetado.

# Projeto final sugerido

Como prática final, nós podemos transformar esse banco em um pequeno sistema Java com PostgreSQL.

As próximas etapas poderiam incluir:

1. Conexão Java com PostgreSQL utilizando JDBC.

2. Classe de conexão.

3. Cadastro de clientes.

4. Cadastro de produtos.

5. Cadastro de categorias.

6. Registro de pedidos.

7. Consulta de vendas.

8. Relatórios.

9. DAO.

10. Integração com Java Swing.

# Conclusão

Ao conhecer MySQL, aprender PostgreSQL se torna muito mais simples porque a base da linguagem SQL continua a mesma.

O ponto principal é perceber que SQL não pertence a apenas um banco de dados.

Comandos como SELECT, INSERT, UPDATE, DELETE, JOIN, GROUP BY e outras consultas fazem parte do conhecimento que poderá ser utilizado em diversos sistemas gerenciadores de banco de dados.

A melhor forma de aprender SQL é praticando.

Quanto mais perguntas nós fizermos aos dados, mais natural será construir as consultas.
