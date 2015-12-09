package fatec.poo.model;

import java.util.ArrayList;

public class Pedido {

    private int numero;
    private String dataEmissaoPedido, dataPagto;
    private boolean status;
    private Cliente cliente;
    private Vendedor vendedor;
    private ArrayList<ItemPedido> itensPedidos;

    public Pedido(int numero, String dataEmissaoPedido) {
        this.numero = numero;
        this.dataEmissaoPedido = dataEmissaoPedido;
        itensPedidos = new ArrayList<ItemPedido>();
    }

    public ArrayList<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }

    public String getDataPagto() {
        return dataPagto;
    }

    public void setDataPagto(String dataPagto) {
        this.dataPagto = dataPagto;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNumero() {
        return numero;
    }

    public String getDataEmissaoPedido() {
        return dataEmissaoPedido;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void addItemPedido(ItemPedido ip) {
        double limite;

        itensPedidos.add(ip);
        ip.setPedido(this);

        limite = cliente.getLimiteDisp() - ip.getProduto().getPrecoUnit() * ip.getQtdeVendida();
        cliente.setLimiteDisp(limite);
        ip.atualizaEstoque();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void removerItemPedido(Integer item) {
        double limite;

        limite = cliente.getLimiteDisp() + itensPedidos.get(item).getProduto().getPrecoUnit() * itensPedidos.get(item).getQtdeVendida();
        cliente.setLimiteDisp(limite);
        itensPedidos.get(item).atualizaEstoque();
        
        itensPedidos.remove(itensPedidos.get(item));
    }
}
