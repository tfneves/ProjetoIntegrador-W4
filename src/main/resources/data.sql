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
