package fatec.poo.model;

import java.util.ArrayList;

public class Hospede extends Pessoa {
    private String cpf;
    private double taxaDesconto;
    private ArrayList<Registro> registros;

    public Hospede(String cpf, String nome) {
        super(nome);
        this.cpf = cpf;
        registros = new ArrayList<>();
    }

    public void setTaxaDesconto(double taxaDesconto) {
        this.taxaDesconto = taxaDesconto;
    }

    public String getCpf() {
        return cpf;
    }

    public double getTaxaDesconto() {
        return taxaDesconto;
    }

    public void addRegistro(Registro reg) {
        registros.add(reg);
    }

    public static boolean validarCPF(String cpf) {
        int digito1 = 0, aux = 1;
        for (int i=0; i<11; i++) {
                if (i != 3 && i != 7) {
                    digito1 += Character.getNumericValue(cpf.charAt(i)) * aux;
                    aux++;
                }
        }
        digito1 %= 11;
        digito1 = (digito1 == 10) ? 0 : digito1;

        int digito2 = 0;
        int multiplicador = 11;
        for (int i = 0; i < 11; i++) {
                if (i != 3 && i != 7) {
                    digito2 += Character.getNumericValue(cpf.charAt(i)) * multiplicador;
                    multiplicador--;
                }
        }
        digito2 += digito1 * 2;

        digito2 *= 10;
        digito2 %= 11;
        digito2 = (digito2 == 10) ? 0 : digito2;

        boolean validez = (Character.getNumericValue(cpf.charAt(12)) == digito1 && Character.getNumericValue(cpf.charAt(13)) == digito2);
         
        return validez;
    }
}
