package br.com.fiap.medibox.model;

import java.sql.Date;
import java.util.ArrayList;

public class ResidenteModel {

    private long idResidente;
    private String nomeResidente;
    private Date dataNascimento;
    private String sexo;
    private String nomeResponsavel;
    private String telResponsavel;
    private String quarto;
    private String observacoes;
    private ArrayList<ResidenteMedicamentoModel> residenteMedicamento;


    public ResidenteModel(long idResidente, String nomeResidente, Date dataNascimento, String sexo, String nomeResponsavel, String telResponsavel, String quarto, String observacoes) {
        this.idResidente = idResidente;
        this.nomeResidente = nomeResidente;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.nomeResponsavel = nomeResponsavel;
        this.telResponsavel = telResponsavel;
        this.quarto = quarto;
        this.observacoes = observacoes;
    }

    public long getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(long idResidente) {
        this.idResidente = idResidente;
    }

    public String getNomeResidente() {
        return nomeResidente;
    }

    public void setNomeResidente(String nomeResidente) {
        this.nomeResidente = nomeResidente;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getTelResponsavel() {
        return telResponsavel;
    }

    public void setTelResponsavel(String telResponsavel) {
        this.telResponsavel = telResponsavel;
    }

    public String getQuarto() {
        return quarto;
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public ArrayList<ResidenteMedicamentoModel> getResidenteMedicamento() {
        return residenteMedicamento;
    }

    public void setResidenteMedicamento(ArrayList<ResidenteMedicamentoModel> residenteMedicamento) {
        this.residenteMedicamento = residenteMedicamento;
    }
}
