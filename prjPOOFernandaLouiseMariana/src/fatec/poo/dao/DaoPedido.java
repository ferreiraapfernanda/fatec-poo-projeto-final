package fatec.poo.dao;

import fatec.poo.model.Cliente;
import fatec.poo.model.ItemPedido;
import fatec.poo.model.Pedido;
import fatec.poo.model.Produto;
import fatec.poo.model.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 0030481411014
 */
public class DaoPedido {

    private Connection conn;

    public DaoPedido(Connection conn) {
        this.conn = conn;
    }

    public Pedido consultar(Integer num) {
        DaoCliente daoCliente = new DaoCliente(conn);
        DaoVendedor daoVendedor = new DaoVendedor(conn);

        Pedido pedido = null;
        ItemPedido itempedido = null;
        Produto produto = null;

        PreparedStatement psPedido, psItem, psProduto = null;
        try {
            psPedido = conn.prepareStatement("SELECT * from POO_PEDIDO where "
                    + "NUMERO = ?");

            psPedido.setInt(1, num);
            ResultSet rsPedido = psPedido.executeQuery();

            if (rsPedido.next() == true) {
                //RE-CRIANDO OS OBJETOS DE CLIENTE E VENDEDOR DO PEDIDO, A PARTIR DOS DADOS DO BANCO
                Cliente c = daoCliente.consultar(rsPedido.getString("CPF_CLIENTE"));
                Vendedor v = daoVendedor.consultar(rsPedido.getString("CPF_VENDEDOR"));

                //RE-CRIANDO O OBJETO PEDIDO
                pedido = new Pedido(num, rsPedido.getString("DATA_PEDIDO"));
                pedido.setCliente(c);
                pedido.setVendedor(v);
                pedido.setStatus(rsPedido.getBoolean("STATUS"));
                pedido.setDataPagto(rsPedido.getString("DATA_PAGTO"));

                //RE-CRIANDO OS ITENS DO PEDIDO, DE ACORDO COM OS VALORES DO SEU PRODUTO
                psItem = conn.prepareStatement("SELECT * FROM POO_ITEM_PEDIDO WHERE NUMERO_PEDIDO = ?");
                psItem.setInt(1, pedido.getNumero());
                ResultSet rsItem = psItem.executeQuery();

                while (rsItem.next() == true) {
                    psProduto = conn.prepareStatement("SELECT * from POO_PRODUTO where "
                            + "COD_PRODUTO = ?");

                    psProduto.setInt(1, rsItem.getInt("CODIGO_PRODUTO"));
                    ResultSet rsProduto = psProduto.executeQuery();

                    if (rsProduto.next() == true) {
                        produto = new Produto(rsItem.getInt("CODIGO_PRODUTO"), rsProduto.getString("DESCRICAO"));
                        produto.setQtdeDisponivel(rsProduto.getInt("QTDE_DISPONIVEL"));
                        produto.setPrecoUnit(rsProduto.getDouble("PRECO_UNIT"));
                        produto.setEstoqueMin(rsProduto.getInt("ESTOQUE_MINIMO"));

                    }
                    itempedido = new ItemPedido(rsItem.getInt("CODIGO_PRODUTO"), rsItem.getInt("QTD_VENDIDA"));
                    itempedido.setProduto(produto);
                    itempedido.setPedido(pedido);

                    pedido.getItensPedidos().add(itempedido);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return (pedido);
    }

    public void inserir(Pedido pedido) {
        DaoItemPedido daoItemPedido = new DaoItemPedido(conn);

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO POO_PEDIDO(NUMERO, CPF_CLIENTE, CPF_VENDEDOR, STATUS, DATA_PEDIDO, DATA_PAGTO) VALUES(?,?,?,?,?,?)");
            ps.setInt(1, pedido.getNumero());
            ps.setString(2, pedido.getCliente().getCpf());
            ps.setString(3, pedido.getVendedor().getCpf());
            ps.setBoolean(4, pedido.getStatus());
            ps.setString(5, pedido.getDataEmissaoPedido());
            ps.setString(6, pedido.getDataPagto());

            ps.execute();

            Integer cont = 0;
            while (cont < pedido.getItensPedidos().size()) {
                daoItemPedido.inserir(pedido.getItensPedidos().get(cont));
                cont++;
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void alterar(Pedido pedido) {
        PreparedStatement psItemBanco = null;
        PreparedStatement psQtdItem = null;
        try {
            psItemBanco = conn.prepareStatement("SELECT * FROM POO_ITEM_PEDIDO WHERE NUMERO_PEDIDO = ?");
            psItemBanco.setInt(1, pedido.getNumero());
            ResultSet rsItemBanco = psItemBanco.executeQuery();
            Boolean flag = false;
            Boolean flagAchou = false;

            //COMPARAÇÃO BANCO/OBJETO
            while (rsItemBanco.next()) {
                flag = false;
                flagAchou = false;
                for (int x = 0; x < pedido.getItensPedidos().size(); x++) {
                    if (pedido.getItensPedidos().get(x).getProduto().getCodigo() == rsItemBanco.getInt("CODIGO_PRODUTO")) {
                        //OBJETO EXISTE NO BANCO E NO OBJETO
                        flagAchou = true;

                        if (pedido.getItensPedidos().get(x).getQtdeVendida() != rsItemBanco.getInt("QTD_VENDIDA")) {
                            //OBJETO TEVE SUA QUANTIDADE VENDIDA ATUALIZADA
                            flag = true;
                            psQtdItem = conn.prepareStatement("UPDATE POO_ITEM_PEDIDO SET QTD_VENDIDA = ? WHERE NUMERO_PEDIDO = ? AND CODIGO_PRODUTO = ?");
                            psQtdItem.setInt(1, pedido.getItensPedidos().get(x).getQtdeVendida());
                            psQtdItem.setInt(2, pedido.getNumero());
                            psQtdItem.setInt(3, pedido.getItensPedidos().get(x).getProduto().getCodigo());
                            psQtdItem.executeQuery();
                        }
                    }
                }
                if (flag == false && flagAchou == false) {
                    //SE O ITEM NÃO EXISTE NO OBJETO (POREM SE ENCONTRA NO BANCO) EXCLUIR DO BANCO
                    psQtdItem = conn.prepareStatement("DELETE FROM POO_ITEM_PEDIDO WHERE NUMERO_PEDIDO = ? AND CODIGO_PRODUTO = ?");
                    psQtdItem.setInt(1, pedido.getNumero());
                    psQtdItem.setInt(2, rsItemBanco.getInt("CODIGO_PRODUTO"));
                    psQtdItem.executeQuery();
                }
            }

            rsItemBanco = psItemBanco.executeQuery();

            //COMPARAÇÃO OBJETO/BANCO
            for (int x = 0; x < pedido.getItensPedidos().size(); x++) {
                rsItemBanco = psItemBanco.executeQuery();
                flag = false;
                while (rsItemBanco.next()) {
                    if (pedido.getItensPedidos().get(x).getProduto().getCodigo() == rsItemBanco.getInt("CODIGO_PRODUTO")) {
                        //OBJETO EXISTE NO BANCO E NO OBJETO
                        flag = true;
                    }
                }
                if (flag == false) {
                    // OBJETO NÃO EXISTE NO BANCO
                    psQtdItem = conn.prepareStatement("INSERT INTO POO_ITEM_PEDIDO VALUES(?, ?, ?)");
                    psQtdItem.setInt(1, pedido.getNumero());
                    psQtdItem.setInt(2, pedido.getItensPedidos().get(x).getProduto().getCodigo());
                    psQtdItem.setInt(3, pedido.getItensPedidos().get(x).getQtdeVendida());
                    psQtdItem.executeQuery();
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public void atualizarLimite(Pedido pedido) {
        PreparedStatement psNovoLimite = null;
        try {
            psNovoLimite = conn.prepareStatement("UPDATE POO_CLIENTE SET LIMITE_DISPONIVEL = ? WHERE CPF = ?");
            psNovoLimite.setDouble(1, pedido.getCliente().getLimiteDisp());
            psNovoLimite.setString(2, pedido.getCliente().getCpf());
            psNovoLimite.executeQuery();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void atualizarEst(Pedido pedido) {

        PreparedStatement psNovoEstoque = null;
        try {
            for (int x = 0; x < pedido.getItensPedidos().size(); x++) {
                psNovoEstoque = conn.prepareStatement("UPDATE POO_PRODUTO SET QTDE_DISPONIVEL = ? WHERE COD_PRODUTO = ?");
                psNovoEstoque.setInt(1, pedido.getItensPedidos().get(x).getProduto().getQtdeDisponivel());
                psNovoEstoque.setInt(2, pedido.getItensPedidos().get(x).getProduto().getCodigo());
                psNovoEstoque.executeQuery();
               
            }

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void excluir(Pedido pedido) {

        DaoItemPedido daoItemPedido = new DaoItemPedido(conn);

        PreparedStatement ps = null;
        try {

            Integer cont = 0;
            while (cont < pedido.getItensPedidos().size()) {
                daoItemPedido.excluir(pedido.getItensPedidos().get(cont));
                cont++;
            }

            ps = conn.prepareStatement("DELETE FROM POO_PEDIDO WHERE NUMERO = ?");
            ps.setInt(1, pedido.getNumero());

            ps.execute();

        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

}
