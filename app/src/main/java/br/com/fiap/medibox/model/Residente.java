package br.com.fiap.medibox.model;

import java.util.Date;
import java.util.List;

public class Residente {
    private int id_Residente;
    private String nomeResidente;
    private Date dataNascimento;
    private String Sexo;
    private String nomeResponsavel;
    private String telResponsavel;
    private String observacoes;
    private String quarto;
    private List<Residente_Medicamento> listaMedicamentos;

    public Residente(int id_Residente, String nomeResidente, Date dataNascimento, String quarto) {
        this.id_Residente = id_Residente;
        this.nomeResidente = nomeResidente;
        this.dataNascimento = dataNascimento;
        this.quarto = quarto;
    }

    public Residente(int id_Residente, String nomeResidente, Date dataNascimento, String sexo, String nomeResponsavel, String telResponsavel, String observacoes, String quarto) {
        this.id_Residente = id_Residente;
        this.nomeResidente = nomeResidente;
        this.dataNascimento = dataNascimento;
        Sexo = sexo;
        this.nomeResponsavel = nomeResponsavel;
        this.telResponsavel = telResponsavel;
        this.observacoes = observacoes;
        this.quarto = quarto;
    }

    public void addMedicamento(Residente_Medicamento medicamento){
        listaMedicamentos.add(medicamento);
    }

    public void delMedicamento (Residente_Medicamento medicamento){
        for (int i = 0; i< listaMedicamentos.size(); i++) {
            Residente_Medicamento m = listaMedicamentos.get(i);
            if (medicamento.getMedicamento().getId_Medicamento() == m.getMedicamento().getId_Medicamento()){
                listaMedicamentos.remove(i);
                break;
            }
        }
    }

    public List<Residente_Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public int getId_Residente() {
        return id_Residente;
    }

    public void setId_Residente(int id_Residente) {
        this.id_Residente = id_Residente;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getQuarto() {
        return quarto;
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }
}


