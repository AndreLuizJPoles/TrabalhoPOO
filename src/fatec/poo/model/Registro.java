package fatec.poo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Registro {
    private int codigo;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private double valorHospedagem;
    private Recepcionista recepcionista;
    private Hospede hospede;
    private Quarto quarto;
    private ArrayList<ServicoQuarto> servicos;

    public Registro(int codigo, LocalDate dataEntrada, Recepcionista recepcionista) {
        this.codigo = codigo;
        this.dataEntrada = dataEntrada;
        this.recepcionista = recepcionista;
        servicos = new ArrayList<>();
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public int getCodigo() {
        return codigo;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public double getValorHospedagem() {
        return valorHospedagem;
    }
    
    public void reservarQuarto(Hospede hospede, Quarto quarto) {
        quarto.reservar();
        setHospede(hospede);
        setQuarto(quarto);
    }
    
    public double liberarQuarto() {
        long dias = ChronoUnit.DAYS.between(dataEntrada, dataSaida);
        valorHospedagem = quarto.liberar(Math.toIntExact(dias));
        double valServicos = 0;
        for(ServicoQuarto s : servicos){
            valServicos += s.getValor();
        }
        return valorHospedagem * (1 - (hospede.getTaxaDesconto()/100)) + valServicos;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
        this.hospede.addRegistro(this);
    }

    public void setRecepcionista(Recepcionista recepcionista) {
        this.recepcionista = recepcionista;
        this.recepcionista.addRegistro(this);
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }
    
    public void addServicoQuarto(ServicoQuarto servico){
        servicos.add(servico);
    }

    public void setValorHospedagem(double valorHospedagem) {
        this.valorHospedagem = valorHospedagem;
    }
    
    
}
