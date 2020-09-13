package br.com.fiap.medibox.model;

public class ItemRemedio {
    private String nomeRemedio;
    private String caixaRemedio;

    public ItemRemedio(String nomeRemedio, String caixaRemedio) {
        this.nomeRemedio = nomeRemedio;
        this.caixaRemedio = caixaRemedio;
    }

    public String getNomeRemedio() {
        return nomeRemedio;
    }

    public String getCaixaRemedio() {
        return caixaRemedio;
    }
}
