# Requisito - 06 - Desafio Final

## Documentos Entregáveis

Os documentos entregáveis exigidos no nível 1 do desafio estão no diretório [Requisito-US06](docs).
Leia o User Story em [Requisito 06 - Avaliação.postman_collection](docs/Requisito%206%20-%20ml-check-closest-warehouse-01.pdf).

## Implementação

Consta uma implementação demo de um sistema de sugestões de armazéns baseado em distância mínima de dois pontos: localização fornecida pelo usuário e localização do armazém (latitude, longitude).
Para consultas de endereço foi utilizada a [API Nominatim](https://nominatim.openstreetmap.org):

-   Consultas do tipo search: [https://nominatim.openstreetmap.org/search?q=](https://nominatim.org/release-docs/latest/api/Search/)
-   Consultas do tipo reverse search: [https://nominatim.openstreetmap.org/reverse?lat=[latitude]&lon=[longitude]](https://nominatim.org/release-docs/latest/api/Reverse/)

Como demo, não há a necerssidade de token para consultas de endereço.

## Rodando a aplicação

Para realizar testes funcionais da aplicação:

```
mvn test
```

O relatório será gerado em [/target/site/jacoco/index.html](/target/site/jacoco/index.html) a aprtir de banco de dados H2, para testes funcionais,
e carregamento de banco de dados por [data.sql](src/main/resources/data.sql).

## Endpoints e Payloads

Para chamar corretamente os endpoints, importe a collection Potman disponibilizada em
[Postman - Requisito 06 - ml-check-closest-warehouse-01](docs/US-06%20Arroxellas.postman_collection.json)
