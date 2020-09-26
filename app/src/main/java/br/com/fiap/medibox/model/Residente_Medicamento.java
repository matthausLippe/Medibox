package br.com.fiap.medibox.model;

import java.sql.Date;
import java.sql.Time;


public class Residente_Medicamento {

    private Medicamento medicamento;
    private String dosagem;
    private String intervalo;
    private Date data_Inicio;
    private Time hora_Inicio;
    private int doses;
    private String gaveta;

    public Residente_Medicamento(Medicamento medicamento, String dosagem, String intervalo,int doses, String gaveta) {
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.intervalo = intervalo;
        this.doses = doses;
        this.gaveta = gaveta;
    }



    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String frequencia) {
        this.intervalo = frequencia;
    }

    public Date getData_Inicio() {
        return data_Inicio;
    }

    public void setData_Inicio(Date data_Inicio) {
        this.data_Inicio = data_Inicio;
    }

    public Time getHora_Inicio() {
        return hora_Inicio;
    }

    public void setHora_Inicio(Time hora_Inicio) {
        this.hora_Inicio = hora_Inicio;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public String getGaveta() {
        return gaveta;
    }

    public void setGaveta(String gaveta) {
        this.gaveta = gaveta;
    }
}
