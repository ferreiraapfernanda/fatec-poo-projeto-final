package fatec.poo.dao;

import fatec.poo.model.Cliente;
import fatec.poo.model.Pedido;
import fatec.poo.model.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

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

        Pedido p = null;

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * from POO_PEDIDO where "
                    + "NUMERO = ?");

            ps.setInt(1, num);
            ResultSet rs = ps.executeQuery();

            if (rs.next() == true) {
                //RE-CRIANDO OS OBJETOS DE CLIENTE E VENDEDOR DO PEDIDO, A PARTIR DOS DADOS DO BANCO
                Cliente c = daoCliente.consultar(rs.getString("CPF_CLIENTE"));
                Vendedor v = daoVendedor.consultar(rs.getString("CPF_VENDEDOR"));

                //RE-CRIANDO O OBJETO PEDIDO
                p = new Pedido(num, rs.getString("DATA_PEDIDO"));
                p.setCliente(c);
                p.setVendedor(v);
                p.setStatus(rs.getBoolean("STATUS"));
                p.setDataPagto(rs.getString("DATA_PAGTO"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return (p);
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

    /* tô testando fazer esse codigo aqui mas não sei como chamar no GUI ;-;
     public DefaultTableModel getItensPedido(Integer numPedido) throws Exception {
     PreparedStatement ps = null;

     DefaultTableModel dtm = new DefaultTableModel() {
     public boolean isCellEditable(int row, int column) {
     return false;
     }
     };
        
     String sql = "SELECT * from POO_ITEM_PEDIDO where NUMERO_PEDIDO = " + numPedido;

     ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery();

     //adiciona as colunas  
     dtm.addColumn("Código");
     dtm.addColumn("Descrição");
     dtm.addColumn("Prec. Unit.");
     dtm.addColumn("Qtde. Vend.");
     dtm.addColumn("SubTotal");

     String descricao;
     Integer qtd_vend;
     Double prec_unit, sub_total;

     Produto prod = null;

     while (rs.next()) { //pega os valores do bd para popular tabela  

     prod = consultarProduto(Integer.parseInt(rs.getString("CODIGO_PRODUTO")));
     descricao = prod.getDescricao();
     prec_unit = prod.getPrecoUnit();
     sub_total = prec_unit * Integer.parseInt(rs.getString("QTD_VENDIDA"));

     dtm.addRow(new String[]{rs.getString("CODIGO_PRODUTO"), descricao, prec_unit.toString(), rs.getString("QTD_VENDIDA"), sub_total.toString()});
     }

     conn.close();
     return dtm;
     }
     */
}
