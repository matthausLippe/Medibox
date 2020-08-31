package br.com.fiap.medibox.model;

import java.sql.Date;
import java.sql.Time;


public class Residente_Medicamento {

    private Medicamento medicamento;
    private String dosagem;
    private String intervalo;
    private Date data_Inicio;
    private Time hora_Inicio;
    private Date data_Fim;
    private Time hora_fim;
    private String gaveta;



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

    public Date getData_Fim() {
        return data_Fim;
    }

    public void setData_Fim(Date data_Fim) {
        this.data_Fim = data_Fim;
    }

    public Time getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(Time hora_fim) {
        this.hora_fim = hora_fim;
    }

    public String getGaveta() {
        return gaveta;
    }

    public void setGaveta(String gaveta) {
        this.gaveta = gaveta;
    }
}
