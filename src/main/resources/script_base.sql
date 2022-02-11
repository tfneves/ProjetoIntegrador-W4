--- CRIA VENDEDOR ---
insert into vendedor (nome) values ('Comprador 1');

--- CRIA CATEGORIA DE PRODUTO ---
insert into produto_categorias (categoria, temperatura_minima) value ('FS', 28);

--- CRIA PRODUTOS ---
insert into produtos (nome, volume, categoria_id) values ('Abacate', 1, 'FS');
insert into produtos (nome, volume, categoria_id) values ('Maçã', 2, 'FS');

--- CRIA ARMAZEM ---
insert into armazem (nome, volume) values ('MLB Cajamar II', 100);

-- CRIA SETOR ---
insert into setor (categoria, volume, armazem_id) values ('FS', 50, 1);

--- CRIA REPRESENTANTE ---
insert into representante (nome, armazem_id) values ('Representante 1', 1);

--- CRIA LOTE ---
insert into lote (data_aquisicao, setor_id, representante_id) values ('2022-01-01', 1, 1);

--- CRIA ANUNCIO DO PRODUTO ---
insert into produto_vendedor
(data_manufatura, data_vencimento, preco, quantidade_atual, quantidade_inicial, temperatura_atual, lote_id, produto_id, vendedor_id)
values ('2023-01-01', '2022-03-03', 10, 10, 10, 30, 1, 1, 1);
insert into produto_vendedor
(data_manufatura, data_vencimento, preco, quantidade_atual, quantidade_inicial, temperatura_atual, lote_id, produto_id, vendedor_id)
values ('2023-01-01', '2022-03-03', 10, 10, 10, 30, 1, 2, 1);

-- CRIA ENDERECO ---
insert into endereco (logradouro, numero, bairro, cidade, estado) values ('Rua Teste 1', 1234, 'Parque Peruche', 'São Paulo', 'São Paulo');

--- CRIA COMPRADOR ---
insert into comprador_teste (nome, telefone , email, data_nascimento, endereco_id) values ('Thomaz', '11 940028922', 'thomaz@gmail.com', '1999-05-07', 1);

--- CRIA STATUS DE PEDIDO ---
insert into status_pedido (status_code, is_disposable) values ('FINALIZADO', false);
insert into status_pedido (status_code, is_disposable) values ('PAGAMENTO_APROVADO', false);
insert into status_pedido (status_code, is_disposable) values ('AGUARDANDO_PAGAMENTO', true);
insert into status_pedido (status_code, is_disposable) values ('CHECKOUT', true);

--- CRIA PEDIDO ---
insert into carrinho (`data`, comprador_id, status_pedido_id) values ('2022-04-05', 1, 1);
insert into produto_carrinho (quantidade, carrinho_id, produto_vendedor_id) values (2, 1, 1);
insert into produto_carrinho (quantidade, carrinho_id, produto_vendedor_id) values (5, 1, 2);



------------------ Comandos de Ajuda -------------------------

-- Para consultar os Status de Pedido cadastrados
-- select * from status_pedido;

-- Para consultar se um carrinho foi criado
-- select * from carrinho c

-- Para consultar se os itens do pedido/carrinho foram criados ou excluídos
-- select * from produto_carrinho;

-- Para consultar se os produtos do carrinho/pedido foram devolvidos ao estoque ** olhar na coluna quantidade_atual **
-- SELECT * from produto_vendedor;
