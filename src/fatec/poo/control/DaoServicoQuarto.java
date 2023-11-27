
package fatec.poo.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fatec.poo.model.ServicoQuarto;
import java.sql.ResultSet;

public class DaoServicoQuarto {
    private Connection conn;
    
    public DaoServicoQuarto(Connection conn) {
         this.conn = conn;
    }
    public void inserir(ServicoQuarto servicoQuarto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO tbServicoQuarto(codigo, descricao, valor) VALUES(?,?,?)");
            ps.setInt(1, servicoQuarto.getCodigo());
            ps.setString(2, servicoQuarto.getDescricao());
            ps.setDouble(3, servicoQuarto.getValor());
                      
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
    public void alterar(ServicoQuarto servicoQuarto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE tbServicoQuarto set descricao = ?, valor = ? " +
                                                 "where codigo = ?");
            ps.setString(1, servicoQuarto.getDescricao());
            ps.setDouble(2, servicoQuarto.getValor());
            ps.setInt(3, servicoQuarto.getCodigo());
            
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
    public  ServicoQuarto consultar (int codigo) {
        ServicoQuarto d = null;
       
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * from tbServicoQuarto where " +
                                                 "codigo = ?");
            
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
           
            if (rs.next() == true) {
                d = new ServicoQuarto (codigo, rs.getString("descricao"));
                d.setValor(rs.getDouble("valor"));
            }
        }
        catch (SQLException ex) { 
             System.out.println(ex.toString());   
        }
        return (d);
    }
    public void excluir(ServicoQuarto ServicoQuarto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM tbServicoQuarto where codigo = ?");
            
            ps.setInt(1, ServicoQuarto.getCodigo());
                      
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
}
