# FATEC_POO_ProjetoFinal
## Definições gerais (classes e métodos)
Projeto para a disciplina de Programação Orientada a Objetos da Faculdade de Tecnologia de Sorocaba. Cadastros de Clientes, Vendedores, Produtos e Pedidos.

O objetivo do projeto era que trabalhassemos com a arquitetura MVC com persistência de dados em um banco.
A principais funções da aplicação são: 
- Manipulação de cadastro de clientes
- Manipulação de cadastro de vendedores
- Manipulação de cadastro de produtos
- Manipulação de emissão de pedidos

1. Criamos um classe abstrata PESSOA (seguindo a definição requerida pelo professor), com campos de CPF, nome, endereço, cidade, CEP, UF, DDD e telefone.
2. Seguindo a classe abstrata, utilizamos como extensão (sub-classe) a classe CLIENTE, com atributos de limite de crédito e limite disponível.
3. Outra extensão é a classe VENDEDOR, com os atributos de salário base e comissão.
4. Para a classe Pedido, são necessários os campos de número, data de emissão do pedido, data de pagamento e status.
Como iremos utilizar uma associação binária entre as classes (cliente-pedido e vendedor-pedido), em ambas a classes definimos um array de pedidos, que serão manipulados de acordo a necessidade da aplicação (adicionar novos pedidos e remover). 
5. Para a classe Item Pedido, teremos o número do item e a quantidade vendida desse item. Como um único pedido pode possuir vários itens, criamos um array de itens na classe PEDIDO, que também será manipulada.
6. Finalmente, para a classe Produto, teremos os atributos de código, descrição, quantidade disponível, preço unitário e estoque mínimo. Cada Item de Pedido terá relação com um Produto cadastrado. 

---

Outras observações referentes ao Projeto: 
- A cada inserção/remoção de um item no pedido, o limite disponível do cliente será modificado. 
- A cada inserção/remoção de um item no pedido, o estoque disponível desse produto deverá ser modificado.
