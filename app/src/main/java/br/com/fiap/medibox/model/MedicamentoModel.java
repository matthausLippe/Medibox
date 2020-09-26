package br.com.fiap.medibox.model;

public class MedicamentoModel {

    private long idMedicamento;
    private String nomeMedicamento;
    private String laboratorio;
    private String dosagem;
    private String descricao;

    public MedicamentoModel(long idMedicamento, String nomeMedicamento, String laboratorio, String dosagem, String descricao) {
        this.idMedicamento = idMedicamento;
        this.nomeMedicamento = nomeMedicamento;
        this.laboratorio = laboratorio;
        this.dosagem = dosagem;
        this.descricao = descricao;
    }

    public MedicamentoModel(long idMedicamento, String nomeMedicamento, String laboratorio, String dosagem) {
        this.idMedicamento = idMedicamento;
        this.nomeMedicamento = nomeMedicamento;
        this.laboratorio = laboratorio;
        this.dosagem = dosagem;
    }

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
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
