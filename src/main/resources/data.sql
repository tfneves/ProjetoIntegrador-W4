INSERT INTO armazem (nome, volume)
VALUES ('TESTE', 200);
INSERT INTO vendedor (nome)
VALUES ('Francisco');
INSERT INTO representante (nome, armazem_id)
VALUES ('Agostinho', 1);
INSERT INTO produto_categorias (categoria, temperatura_minima)
VALUES ('FS', 19.0);
INSERT INTO produtos (nome, volume, categoria_id)
values ('avocado', 1, 'FS');
INSERT INTO setor (categoria, volume, armazem_id)
VALUES ('FS', 50, 1);
INSERT INTO setor (categoria, volume, armazem_id)
VALUES ('FS', 50, 1);
INSERT INTO lote (setor_id, representante_id)
VALUES (1, 1);
INSERT INTO lote (setor_id, representante_id)
VALUES (1, 1);
INSERT INTO produto_vendedor (data_manufatura, data_vencimento, preco, quantidade_atual, quantidade_inicial,
                              temperatura_atual,
                              lote_id, produto_id, vendedor_id)
VALUES ('2022-01-01', '2022-03-03', 10, 5, 5, 20, 1, 1, 1);

