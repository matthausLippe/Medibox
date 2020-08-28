package br.com.fiap.medibox.model;

import java.sql.Date;
import java.util.List;

public class Residente {
    private int id_Residente;
    private String nomeResidente;
    private Date dataNascimento;
    private String nomeResponsavel;
    private String telResponsavel;
    private String observacoes;
    private List<Residente_Medicamento> listaMedicamentos;

    public Residente(int id_Residente, String nomeResidente, Date dataNascimento) {
        this.id_Residente = id_Residente;
        this.nomeResidente = nomeResidente;
        this.dataNascimento = dataNascimento;
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


}
