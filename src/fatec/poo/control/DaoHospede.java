package fatec.poo.control;

import fatec.poo.model.Hospede;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Julia
 */
public class DaoHospede {

    private Connection conn;

    public DaoHospede(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Hospede hospede) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO tbhospede(cpf, nome, endereco, telefone, taxadesconto) VALUES(?,?,?,?,?)");
            ps.setString(1, hospede.getCpf());
            ps.setString(2, hospede.getNome());
            ps.setString(3, hospede.getEndereco());
            ps.setString(4, hospede.getTelefone());
            ps.setDouble(5, hospede.getTaxaDesconto());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public void alterar(Hospede hospede) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE tbhospede set nome = ?, endereco = ?, telefone = ?, taxadesconto = ? "
                    + "where cpf = ?");

            ps.setString(1, hospede.getNome());
            ps.setString(2, hospede.getEndereco());
            ps.setString(3, hospede.getTelefone());
            ps.setDouble(4, hospede.getTaxaDesconto());
            ps.setString(5, hospede.getCpf());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    public Hospede consultar(String cpf) {
        Hospede h = null;

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT * from tbhospede where "
                    + "cpf = ?");

            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next() == true) {
                h = new Hospede(cpf, rs.getString("nome"));
                h.setEndereco(rs.getString("Endereco"));
                h.setTelefone(rs.getString("Telefone"));
                h.setTaxaDesconto(rs.getDouble("TaxaDesconto"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return (h);
    }

    public void excluir(Hospede hospede) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM tbhospede where cpf = ?");

            ps.setString(1, hospede.getCpf());

            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
