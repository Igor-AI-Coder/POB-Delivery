# POB-Delivery

Projeto de Persistência de Objetos (POB) desenvolvido para a disciplina de **Persistência de Objetos** utilizando **DB4O**.

## Descrição

Sistema de gerenciamento de pedidos de delivery implementado com banco de dados orientado a objetos. O projeto demonstra operações básicas de CRUD (Create, Read, Update, Delete) e consultas SODA Query.

## Tecnologias

- Java - Linguagem de programação
- DB4O - Banco de dados orientado a objetos

## Estrutura do Projeto

    src/
      modelo/
        Cliente.java
        Pedido.java
        Produto.java
      appconsole/
        Cadastrar.java
        Listar.java
        Consultar.java
        Alterar.java
        Deletar.java
      util/
        Util.java
        ControleID.java
        ip.properties


## Consultas Implementadas

1. Pedidos por Data - Busca pedidos de uma data especifica
2. Pedidos por Preco - Busca pedidos com produtos acima de um valor
3. Clientes com Multiplos Pedidos - Busca clientes com mais de 2 pedidos de um produto

## Autor

Desenvolvido por Igor, Emanoel e Rogerio para a disciplina de Persistencia de Objetos - IFPB

