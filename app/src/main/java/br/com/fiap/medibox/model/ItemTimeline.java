package br.com.fiap.medibox.model;

public class ItemTimeline {
    private String nome;
    private String quantidade;
    private String horario;
    private String gaveta;
    private String remedio;

    public ItemTimeline(String nome, String quantidade, String horario, String gaveta, String remedio) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.horario = horario;
        this.gaveta = gaveta;
        this.remedio = remedio;
    }

    public String getNome() {
        return nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public String getHorario() {
        return horario;
    }

    public String getGaveta() {
        return gaveta;
    }

    public String getRemedio() {
        return remedio;
    }
}
