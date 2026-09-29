select * from produto

select * from categoria





SELECT produto.id, produto.nome, produto.preco, 
categoria.nome AS categoria
FROM produto  INNER JOIN categoria 
ON produto.categoria_id = categoria.id 
**************************************************************
SELECT p.id, p.nome, p.preco,
    c.nome AS categoria
FROM produto p
INNER JOIN categoria c
ON p.categoria_id = c.id



SELECT
    p.nome AS produto,
    c.nome AS categoria
FROM produto p
LEFT JOIN categoria c
ON p.categoria_id = c.id



UPDATE categoria
SET nome = 'Brinquedos'
WHERE id



CREATE TABLE pedido (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    data_pedido DATE DEFAULT CURRENT_DATE,
    status VARCHAR(30) DEFAULT 'ABERTO',
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES cliente(id)
)
select * from pedido

CREATE TABLE item_pedido (
    id SERIAL PRIMARY KEY,
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
)


INSERT INTO pedido
(cliente_id, status)
VALUES
(1, 'FINALIZADO'),
(2, 'FINALIZADO'),
(1, 'ABERTO');

INSERT INTO item_pedido
(pedido_id, produto_id, quantidade, valor_unitario)
VALUES
(1, 1, 2, 45.90),
(1, 2, 1, 89.90),
(2, 3, 1, 899.90),
(3, 1, 1, 45.90);


SELECT
    p.id AS pedido,
    c.nome AS cliente,
    p.data_pedido,
    p.status
FROM pedido p
INNER JOIN cliente c
ON p.cliente_id = c.id;


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


SELECT pro.nome,
       item.quantidade,
	   item.valor_unitario,
	   item.quantidade * item.valor_unitario AS subtotal
FROM item_pedido item
INNER JOIN produto pro
ON item.produto_id = pro.id


SELECT COUNT(*)
FROM cliente;


SELECT SUM(estoque)
FROM produto;

SELECT AVG(preco) AS preco_medio
FROM produto;

SELECT MIN(preco) AS menor_preco
FROM produto;

select * from produto

SELECT c.nome AS categoria,
       Count(p.id) AS quantidade
FROM categoria c
LEFT JOIN produto p
ON p.categoria_id = c.id
GROUP BY c.nome

******************** venda total por cliente
SELECT c.nome AS cliente,
        SUM(i.quantidade * i.valor_unitario) AS total_comprado
FROM cliente c
INNER JOIN pedido p
ON p.cliente_id = c.id
INNER JOIN item_pedido i
ON i.pedido_id = p.id
GROUP BY c.nome

************** venda total por pedido  ************
SELECT pedido_id,
	   SUM(quantidade * valor_unitario) AS total
FROM item_pedido
GROUP BY pedido_id

SELECT * FROM item_pedido


SELECT * FROM produto
WHERE preco BETWEEN 10 AND 100

SELECT nome, preco,
CASE
    WHEN preco < 100 THEN 'Baixo valor'
	WHEN preco <= 1000 THEN 'Valor médio'
	ELSE 'Alto Valor'
END	AS faixa_preco
FROM produto








		

