package fatec.poo.control;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;

import fatec.poo.model.Quarto;

public class DaoQuarto {
    
    private Connection conn;
    
    public DaoQuarto(Connection conn) {
         this.conn = conn;
    }
      public void inserir(Quarto quarto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO tbQuarto(numero, tipo, valorDiaria, situacao) VALUES(?,?,?,?)");
            
            ps.setInt(1, quarto.getNumero());
            ps.setString(2, quarto.getTipo());
            ps.setDouble(3, quarto.getValorDiaria());
            ps.setInt(4, 0);
            
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
      
    public void alterar(Quarto quarto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE tbQuarto set valorDiaria = ?, tipo = ?, situacao = ?" +
                                                 "where numero = ?");
            
            ps.setDouble(1, quarto.getValorDiaria());
            ps.setString(2, quarto.getTipo());
            
            if(quarto.getSituacao()){
                ps.setInt(3, 1);
            }else{
                ps.setInt(3, 0);
            }
            
            ps.setInt(4, quarto.getNumero());
            
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
    
    public  Quarto consultar (int numero) {
        Quarto q = null;
       
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * from tbQuarto where " +
                                                 "numero = ?");
            
            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();
           
            if (rs.next() == true) {
                q = new Quarto (numero, rs.getString("tipo"), rs.getDouble("valorDiaria"));
                if(rs.getInt("situacao") == 0){
                    q.setSituacao(false);
                }else{
                    q.setSituacao(true);
                }                
            }
        }
        catch (SQLException ex) { 
             System.out.println(ex.toString());   
        }
        return (q);
    }
    
    public void excluir(Quarto quarto) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM tbQuarto where numero = ?");
            
            ps.setInt(1, quarto.getNumero());
                      
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
}

