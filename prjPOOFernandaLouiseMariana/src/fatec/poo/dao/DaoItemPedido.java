package fatec.poo.dao;

import fatec.poo.model.ItemPedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author 0030481411014
 */
public class DaoItemPedido {
    
    private Connection conn;

    public DaoItemPedido(Connection conn) {
        this.conn = conn;
    }
    
    public void inserir(ItemPedido item) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO POO_ITEM_PEDIDO(NUMERO_PEDIDO, CODIGO_PRODUTO, QTD_VENDIDA) VALUES(?,?,?)");
            ps.setInt(1, item.getPedido().getNumero());
            ps.setInt(2, item.getProduto().getCodigo());
            ps.setInt(3, item.getQtdeVendida());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
        
    public void excluir(ItemPedido item){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM POO_ITEM_PEDIDO WHERE NUMERO_PEDIDO = ?");
            ps.setInt(1, item.getPedido().getNumero());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
}
