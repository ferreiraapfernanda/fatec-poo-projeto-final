# FATEC - Programação Orientada a Objetos - Projeto Final

## Colaboradoras: 
* Louise Constantino [@LNazgul](https://github.com/LNazgul)
* Mariana Sabino [@metamorfomahga](https://github.com/metamorfomahga)
* Fernanda Aparecida [@fromnanda](https://github.com/fromnanda)

## Definições gerais (classes e métodos)
Projeto para a disciplina de Programação Orientada a Objetos da Faculdade de Tecnologia de Sorocaba (*FATEC Sorocaba*). 
Aplicação para os **cadastros de Clientes, Vendedores, Produtos e Pedidos**. <br>
<br>
O objetivo do projeto era que trabalhassemos com a arquitetura MVC com persistência de dados em um banco.
A principais funções da aplicação são: 
- Manipulação do cadastro de clientes
- Manipulação do cadastro de vendedores
- Manipulação do cadastro de produtos
- Manipulação da emissão de pedidos

<br>
1. Criamos um classe abstrata **_Pessoa_** (seguindo a definição requerida pelo professor), com campos de CPF, nome, endereço, cidade, CEP, UF, DDD e telefone.<br>

2. Seguindo a classe abstrata, utilizamos como extensão (sub-classe) a classe **_Cliente_**, com atributos de limite de crédito e limite disponível.<br>

3. Outra extensão é a classe **_Vendedor_**, com os atributos de salário base e comissão.<br>
4. Para a classe **_Pedido_**, são necessários os campos de número, data de emissão do pedido, data de pagamento e status.<br>
Como iremos utilizar uma associação binária entre as classes (cliente-pedido e vendedor-pedido), em ambas a classes definimos um array de pedidos, que serão manipulados de acordo a necessidade da aplicação (adicionar novos pedidos e remover). <br>

5. Para a classe **_Item Pedido_**, teremos o número do item e a quantidade vendida desse item. Como um único pedido pode possuir vários itens, criamos um array de itens na classe PEDIDO, que também será manipulada.<br>

6. Finalmente, para a classe **_Produto_**, teremos os atributos de código, descrição, quantidade disponível, preço unitário e estoque mínimo. Cada Item de Pedido terá relação com um Produto cadastrado. <br>

---

Outras observações referentes ao Projeto: 
- A cada inserção/remoção de um _item no pedido_, o limite disponível do cliente será modificado. 
- A cada inserção/remoção de um _item no pedido_, o estoque disponível desse produto deverá ser modificado.
