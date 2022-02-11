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
subirá o Spring. Para o banco de dados, será necessário criar um arquivo <b><i>application.properties</i></b> e configurá-lo. Após a configuração, basta executar o [script_base.sql](src/main/resources/script_base.sql) para ter os dados necessários para o teste do endpoint.


## Endpoint e Payload

Para chamar corretamente o endpoint construído, basta realizar uma requisição do tipo <b>PUT</b> para o seguinte endpoint:
```
/api/v1/orders/updateCartStatus
```

Passando o seguinte Payload:
```JSON
{
    "carrinho_id": 1,
    "statusCode": "PAGAMENTO_APROVADO"
}
```
