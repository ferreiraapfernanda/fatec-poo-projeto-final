package fatec.poo.dao;

import fatec.poo.model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoProduto {

    private Connection conn;
    
    public DaoProduto(Connection conn) {
        this.conn = conn;
    }
    
    public void inserir(Produto produto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO POO_PRODUTO(COD_PRODUTO, DESCRICAO, QTDE_DISPONIVEL, PRECO_UNIT, ESTOQUE_MINIMO) VALUES(?,?,?,?,?)");        
            ps.setInt(1, produto.getCodigo());
            ps.setString(2, produto.getDescricao());
            ps.setInt(3, produto.getQtdeDisponivel());
            ps.setDouble(4, produto.getPrecoUnit());
            ps.setInt(5, produto.getEstoqueMin());
            

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public Produto consultar(Integer cod) {
        Produto p = null;

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * from POO_PRODUTO where "
                    + "COD_PRODUTO = ?");

            ps.setInt(1, cod);
            ResultSet rs = ps.executeQuery();
            

            if (rs.next() == true) {
                p = new Produto(cod, rs.getString("DESCRICAO"));
                p.setQtdeDisponivel(rs.getInt("QTDE_DISPONIVEL"));
                p.setPrecoUnit(rs.getDouble("PRECO_UNIT"));
                p.setEstoqueMin(rs.getInt("ESTOQUE_MINIMO"));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return (p);
    }
    
    public void alterar(Produto produto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE POO_PRODUTO set DESCRICAO = ?, QTDE_DISPONIVEL = ?, PRECO_UNIT = ?, ESTOQUE_MINIMO = ? "
                    + "where COD_PRODUTO = ?");
            
            ps.setString(1, produto.getDescricao());
            ps.setInt(2, produto.getQtdeDisponivel());
            ps.setDouble(3, produto.getPrecoUnit());
            ps.setInt(4, produto.getEstoqueMin());
            
            ps.setInt(5, produto.getCodigo());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
       
    public void excluir(Produto produto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM POO_PRODUTO where COD_PRODUTO = ?");

            ps.setInt(1, produto.getCodigo());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
    
    
    
}
