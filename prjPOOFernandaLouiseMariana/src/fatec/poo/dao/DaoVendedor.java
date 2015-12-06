package fatec.poo.dao;

import fatec.poo.model.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoVendedor {

    private Connection conn;

    public DaoVendedor(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Vendedor vendedor) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO POO_VENDEDOR(CPF, NOME, ENDERECO, CIDADE, CEP, UF, TELEFONE_DDD, TELEFONE_NUMERO, SALARIO_BASE, COMISSAO) VALUES(?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, vendedor.getCpf());
            ps.setString(2, vendedor.getNome());
            ps.setString(3, vendedor.getEndereco());
            ps.setString(4, vendedor.getCidade());
            ps.setString(5, vendedor.getCep());
            ps.setString(6, vendedor.getUf());
            ps.setString(7, vendedor.getDdd());
            ps.setString(8, vendedor.getTelefone());
            ps.setDouble(9, vendedor.getSalarioBase());
            ps.setDouble(10, vendedor.getComissao());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public Vendedor consultar(String cpf) {
        Vendedor v = null;

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * from POO_VENDEDOR where "
                    + "CPF = ?");

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next() == true) {
                v = new Vendedor(cpf, rs.getString("NOME"), rs.getDouble("SALARIO_BASE"));
                v.setEndereco(rs.getString("ENDERECO"));
                v.setCidade(rs.getString("CIDADE"));
                v.setCep(rs.getString("CEP"));
                v.setUf(rs.getString("UF"));
                v.setDdd(rs.getString("TELEFONE_DDD"));
                v.setTelefone(rs.getString("TELEFONE_NUMERO"));
                v.setComissao(rs.getDouble("COMISSAO"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return (v);
    }

    public void alterar(Vendedor vendedor) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE POO_VENDEDOR set NOME = ?, ENDERECO = ?, CIDADE = ?, CEP = ?, UF = ?, TELEFONE_DDD = ?, TELEFONE_NUMERO = ?, SALARIO_BASE = ?, COMISSAO = ?  "
                    + "where CPF = ?");

            ps.setString(1, vendedor.getNome());
            ps.setString(2, vendedor.getEndereco());
            ps.setString(3, vendedor.getCidade());
            ps.setString(4, vendedor.getCep());
            ps.setString(5, vendedor.getUf());
            ps.setString(6, vendedor.getDdd());
            ps.setString(7, vendedor.getTelefone());
            ps.setDouble(8, vendedor.getSalarioBase());
            ps.setDouble(9, vendedor.getComissao());

            ps.setString(10, vendedor.getCpf());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void excluir(Vendedor vendedor) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM POO_VENDEDOR where CPF = ?");

            ps.setString(1, vendedor.getCpf());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
