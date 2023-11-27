package fatec.poo.control;

import fatec.poo.model.Hospede;
import fatec.poo.model.Quarto;
import fatec.poo.model.Recepcionista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fatec.poo.model.Registro;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DaoRegistro {
    private Connection conn;
    
    public DaoRegistro(Connection conn) {
         this.conn = conn;
    }
    
    public  Registro consultar (int codigo) {
        Registro r = null;
       
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * from tbRegistro where " +
                                                 "codigo = ?");
            
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
           
            if (rs.next() == true) {         
                Recepcionista rec = null;
                Hospede hos = null;
                Quarto quarto = null;
                LocalDate data = null;
                int regFunc;
                DaoRecepcionista daoRecepcionista = new DaoRecepcionista(conn);
                DaoHospede daoHospede = new DaoHospede(conn);
                DaoQuarto daoQuarto = new DaoQuarto(conn);
                
                data = rs.getDate("dataEntrada").toLocalDate();
                
                regFunc = rs.getInt("regFuncRecepcionista");
                rec = daoRecepcionista.consultar(regFunc);
                
                r = new Registro (codigo, data, rec);
                
                if(rs.getDate("dataSaida") != null){
                    r.setDataSaida(rs.getDate("dataSaida").toLocalDate());
                    r.setValorHospedagem(rs.getDouble("valorHospedagem"));
                }
                
                hos = daoHospede.consultar(rs.getString("cpfHospede"));
                r.setHospede(hos);
                
                quarto = daoQuarto.consultar(rs.getInt("numeroQuarto"));
                r.setQuarto(quarto);                
            }
        }
        catch (SQLException ex) { 
             System.out.println(ex.toString());   
        }
        return (r);
    }
    
    public void inserir(Registro registro) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO tbRegistro(codigo, regfuncrecepcionista, cpfhospede, numeroquarto, dataentrada) VALUES(?,?,?,?,?)");
            ps.setInt(1, registro.getCodigo());
            ps.setInt(2, registro.getRecepcionista().getRegFunc());
            ps.setString(3, registro.getHospede().getCpf());
            ps.setInt(4, registro.getQuarto().getNumero());
            ps.setDate(5, Date.valueOf(registro.getDataEntrada()));
                      
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
    
    public void alterar(Registro registro) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE tbRegistro set regfuncrecepcionista = ?, cpfhospede = ?, numeroquarto = ?, dataentrada = ?, datasaida = ?, valorhospedagem = ?" +
                                                 "where codigo = ?");
            
            ps.setInt(1, registro.getRecepcionista().getRegFunc());
            ps.setString(2, registro.getHospede().getCpf());
            ps.setInt(3, registro.getQuarto().getNumero());
            ps.setDate(4, Date.valueOf(registro.getDataEntrada()));
            ps.setDate(5, Date.valueOf(registro.getDataSaida()));
            ps.setDouble(6, registro.getValorHospedagem());
            ps.setInt(7, registro.getCodigo());
           
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
    
    public void excluir(Registro registro) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM tbRegistro where codigo = ?");
            
            ps.setInt(1, registro.getCodigo());
                      
            ps.execute();
        } catch (SQLException ex) {
             System.out.println(ex.toString());   
        }
    }
}