# POB-Delivery

Sistema de gerenciamento de pedidos de delivery desenvolvido na disciplina de **Persistência de Objetos**, com evolução em diferentes tecnologias de persistência.

## Visão Geral

O projeto modela um cenário de delivery com três entidades principais:

- **Cliente**: realiza pedidos.
- **Pedido**: pertence a um cliente e contém produtos.
- **Produto**: pode estar presente em vários pedidos.

### Relações
- Um **Cliente** possui vários **Pedidos**.  
- Um **Pedido** possui vários **Produtos**.

## Versões do Projeto

- **V1:** DB4O (banco orientado a objetos)  
- **V2:** JPA + Hibernate (ORM relacional)  
- **V3:** MongoDB (NoSQL)

## Autor

Desenvolvido por Igor, Emanoel e Rogerio — IFPB
