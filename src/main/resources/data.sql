INSERT INTO armazem (nome, volume) VALUES ('TESTE', 200);
INSERT INTO vendedor (nome) VALUES ('Francisco');
INSERT INTO representante (nome, armazem_id) VALUES ('Agostinho', 1);
INSERT INTO produto_categorias (categoria, temperatura_minima) VALUES ('FS', 19.0);
INSERT INTO produtos (nome, volume, categoria_id) values ('avocado', 1, 'FS');
INSERT INTO produtos (nome, volume, categoria_id) values ('manzana', 1, 'FS');
INSERT INTO setor (categoria, volume, armazem_id) VALUES ('FS', 50, 1);
INSERT INTO lote (setor_id, representante_id) VALUES (1, 1);
INSERT INTO produto_vendedor (data_manufatura, data_vencimento, preco, quantidade_atual, quantidade_inicial, temperatura_atual, lote_id, produto_id, vendedor_id)
VALUES ('2022-01-01', '2100-03-03', 10, 5, 5, 20, 1, 1, 1);
INSERT INTO produto_vendedor (data_manufatura, data_vencimento, preco, quantidade_atual, quantidade_inicial, temperatura_atual, lote_id, produto_id, vendedor_id)
VALUES ('2022-01-01', '2100-03-03', 10, 5, 5, 20, 1, 2, 1);
INSERT INTO comprador_teste (data_nascimento, email, nome, telefone) VALUES('1999-11-26', 'm@gmail.com', 'Marcelo', '11999999999');
INSERT INTO comprador_teste (data_nascimento, email, nome, telefone) VALUES('1999-11-26', 't@gmail.com', 'Thomaz', '11999999999');
INSERT into status_pedido (status_code) VALUES ('TESTE');
INSERT into status_pedido (status_code) VALUES ('FINALIZADO');
INSERT INTO carrinho (data, comprador_id, status_pedido_id) VALUES ('2022-02-09', 1, 2);
INSERT INTO produto_carrinho (quantidade, carrinho_id, produto_vendedor_id) VALUES (1, 1, 1);
INSERT INTO carrinho (data, comprador_id, status_pedido_id) VALUES ('2022-02-09', 2, 2);
INSERT INTO produto_carrinho (quantidade, carrinho_id, produto_vendedor_id) VALUES (1, 2, 1);
INSERT INTO classificacao_avaliacao (classificacao) VALUES ('ZERO');
INSERT INTO classificacao_avaliacao (classificacao) VALUES ('UM');
INSERT INTO classificacao_avaliacao (classificacao) VALUES ('DOIS');
INSERT INTO classificacao_avaliacao (classificacao) VALUES ('TRES');
INSERT INTO classificacao_avaliacao (classificacao) VALUES ('QUATRO');
INSERT INTO classificacao_avaliacao (classificacao) VALUES ('CINCO');
