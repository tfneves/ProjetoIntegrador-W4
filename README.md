# Requisito - 06 - Desafio Final

## Documentos Entregáveis

Os documentos entregáveis exigidos no nível 1 do desafio estão no diretório [Requisito-US06](Requisito-US06).

## Implementação

Para o requisito 06, decidi construir um CRUD básico que simulasse o sistema de avaliação dos anúncios de produtos no
Mercado Livre. Foram implementadas 5 rotas desse processo, criação de uma avaliação, exclusão, atualização do
comentário, recuperação de uma avaliação pelo id e recuperação de todas as avaliações de um anúncio de um produto a
partir do id desse anúncio.

## Rodando a aplicação

Para realizar testes funcionais da aplicação, basta inicializá-la a partir da classe main
[ProjetoIntegradorApplication](src/main/java/br/com/meliw4/projetointegrador/ProjetoIntegradorApplication.java), que
subirá o Spring em conjunto com o banco de dados H2 configurado em
[application.properties](src/main/resources/application.properties) e carregado com dados iniciais a partir da execução
do [data.sql](src/main/resources/data.sql).


## Endpoints e Payloads

Para chamar corretamente os endpoints construídos, basta importar no Postman a collection disponibilizada em
[Requisito 06 - Avaliação.postman_collection](Requisito-US06/Requisito%2006%20-%20Avaliação.postman_collection.json).
