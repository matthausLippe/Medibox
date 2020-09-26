package br.com.fiap.medibox.model;

import java.sql.Date;
import java.sql.Time;

public class ResidenteMedicamentoModel {

    private long idResidenteMedicamento;
    private long idResidente;
    private long idMedicamento;
    private long idCliente;
    private long idGaveta;
    private String dosagem;
    private double intervalo;
    private Date dataInicio;
    private Time horaInicio;
    private int doses;

    public long getIdResidenteMedicamento() {
        return idResidenteMedicamento;
    }

    public void setIdResidenteMedicamento(long idResidenteMedicamento) {
        this.idResidenteMedicamento = idResidenteMedicamento;
    }

    public long getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(long idResidente) {
        this.idResidente = idResidente;
    }

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getIdGaveta() {
        return idGaveta;
    }

    public void setIdGaveta(long idGaveta) {
        this.idGaveta = idGaveta;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public double getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(double intervalo) {
        this.intervalo = intervalo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public ResidenteMedicamentoModel(long idResidenteMedicamento, long idResidente, long idMedicamento, long idCliente,
                                     long idGaveta, String dosagem, double intervalo, Date dataInicio, Time horaInicio, int doses) {
        this.idResidenteMedicamento = idResidenteMedicamento;
        this.idResidente = idResidente;
        this.idMedicamento = idMedicamento;
        this.idCliente = idCliente;
        this.idGaveta = idGaveta;
        this.dosagem = dosagem;
        this.intervalo = intervalo;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.doses = doses;


    }
}
