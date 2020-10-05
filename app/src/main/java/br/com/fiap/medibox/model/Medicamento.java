package br.com.fiap.medibox.model;

public class Medicamento {

    private int id_Medicamento;
    private String nomeMedicamento;
    private String laboratorio;
    private String dosagem;
    private String descricao;

    public Medicamento(String nomeMedicamento, String laboratorio, String dosagem, String descricao) {
        this.nomeMedicamento = nomeMedicamento;
        this.laboratorio = laboratorio;
        this.dosagem = dosagem;
        this.descricao = descricao;
    }

    public Medicamento(long id, String paracetamol, String medley, String s, String s1) {
    }

    public int getId_Medicamento() {
        return id_Medicamento;
    }

    public void setId_Medicamento(int id_Medicamento) {
        this.id_Medicamento = id_Medicamento;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
