package fatec.poo.model;

public class ItemPedido {
   
    private int numeroItem, qtdeVendida;
    private Pedido pedido;
    private Produto produto;

    public ItemPedido(int numeroItem, int qtdeVendida) {
        this.numeroItem = numeroItem;
        this.qtdeVendida = qtdeVendida;
    }

    public Pedido getPedido() {
        return pedido;
    }
    
    public int getQtdeVendida() {
        return qtdeVendida;
    }

    public void setQtdeVendida(int qtdeVendida) {
        this.qtdeVendida = qtdeVendida;
    }

    public int getNumeroItem() {
        return numeroItem;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public void atualizaEstoque(){
        int quantidade;
        
        quantidade = produto.getQtdeDisponivel() - qtdeVendida;
        produto.setQtdeDisponivel(quantidade);
    }   
}