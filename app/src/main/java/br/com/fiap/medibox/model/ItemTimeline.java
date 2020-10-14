package br.com.fiap.medibox.model;

import java.sql.Date;

public class ItemTimeline {
    private long idTimeline;
    private String nome;
    private String medicamento;
    private Date dataHora;
    private String dose;
    private String telResponsavel;
    private String observacao;
    private String gaveta;
    public static int MEDICADO = 1;
    public static int NAO_MEDICADO = 0;

    private int situacao;
    private String intervalo;


    public ItemTimeline(String nome, String dose, String intervalo, Date dataHora, String gaveta, String medicamento,String telResponsavel,String observacao, int situacao) {
        this.nome = nome;//Residente
        this.dose = dose;//ResidenteMedicamento
        this.intervalo = intervalo;//ResidenteMedicamento
        this.dataHora = dataHora;//TimeLine
        this.gaveta = gaveta;//ResidenteMedicamento
        this.medicamento = medicamento; //Medicamento
        this.situacao = situacao;//TimeLine
        this.telResponsavel = telResponsavel;
        this.observacao = observacao;
    }

    public ItemTimeline() {

    }

    public long getIdTimeline() {
        return idTimeline;
    }

    public void setIdTimeline(long idTimeline) {
        this.idTimeline = idTimeline;
    }

    public String getTelResponsavel() {
        return telResponsavel;
    }

    public void setTelResponsavel(String telResponsavel) {
        this.telResponsavel = telResponsavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getGaveta() {
        return gaveta;
    }

    public void setGaveta(String gaveta) {
        this.gaveta = gaveta;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }
}
