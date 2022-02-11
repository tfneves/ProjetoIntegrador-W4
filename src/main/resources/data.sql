INSERT INTO armazem
(id, nome, volume, lat, lon)
VALUES(1, 'Armazem1', 111.0, -23.3496144, -46.8760845);
INSERT INTO armazem
(id, nome, volume, lat, lon)
VALUES(2, 'Armazem2', 222.0, -23.5582481, -46.709784);
INSERT INTO armazem
(id, nome, volume, lat, lon)
VALUES(3, 'Armazem3', 333.0, -23.7380938, -46.496416);
INSERT INTO armazem
(id, nome, volume, lat, lon)
VALUES(4, 'Armazem4', 444.0, -23.1998828, -45.9580593);
INSERT INTO armazem
(id, nome, volume, lat, lon)
VALUES(5, 'Armazem5', 555.0, -22.7328026, -47.177083);

INSERT INTO vendedor (nome)
VALUES ('Francisco');
INSERT INTO representante (nome, armazem_id)
VALUES ('Agostinho', 1);
INSERT INTO produto_categorias (categoria, temperatura_minima)
VALUES ('FS', 19.0);
INSERT INTO produtos (nome, volume, categoria_id) values ('avocado', 1, 'FS');
INSERT INTO setor (categoria, volume, armazem_id)
VALUES ('FS', 50, 1);
INSERT INTO lote (setor_id, representante_id)
VALUES (1, 1);
INSERT INTO produto_vendedor (data_manufatura, data_vencimento, preco, quantidade_atual, quantidade_inicial,
                              temperatura_atual,
                              lote_id, produto_id, vendedor_id)
VALUES ('2022-01-01', '2022-03-03', 10, 5, 5, 20, 1, 1, 1);

