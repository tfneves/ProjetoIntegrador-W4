# Requisito 06

## Documentos Entregáveis

Os documentos entregáveis exigidos no nível 1 do desafio estão no diretório [Requisito_US06](Requisito_US06).

## Implementação

Para este requisito, decidi construir uma API que possibilita a alteração do status de um carrinho, de forma que, 
seja possível a identificação de um carrinho abandonado de acordo com seu status atual, e evitar o represamento 
de produtos nesses carrinhos abandonados. <br>
Os carrinhos que forem definidos com um status do tipo <b><i>Disposable</i> (descartável)</b>, serão excluídos depois de algum tempo e, os produtos serão devolvidos ao estoque.<br>
Foi implementado apenas uma rota do tipo PUT para realizar o procedimento e solucionar o problema descrito no anteriormente.


## Rodando a aplicação

Para realizar testes funcionais da aplicação, basta inicializá-la a partir da classe main
[ProjetoIntegradorApplication](src/main/java/br/com/meliw4/projetointegrador/ProjetoIntegradorApplication.java), que
subirá o Spring em conjunto com o banco de dados H2 configurado em
[application.properties](src/main/resources/application.properties) e carregado com dados iniciais a partir da execução
do [data.sql](src/main/resources/data.sql).


## Endpoints e Payloads

Para chamar corretamente os endpoints construídos, basta importar no Postman a collection disponibilizada em
[Requisito 06 - Avaliação.postman_collection](Requisito-US06/Requisito%2006%20-%20Avaliação.postman_collection.json).
