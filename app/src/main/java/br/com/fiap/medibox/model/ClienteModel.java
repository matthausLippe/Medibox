package br.com.fiap.medibox.model;

public class ClienteModel {

    private long idCliente;
    private String nomeCliente;
    private String cpfCnpj;
    private String email;
    private String senha;
    private String telefone;
    private long idEndereco;

    public ClienteModel(long idCliente, String nomeCliente, String cpfCnpj, String email, String senha, String telefone, long idEndereco) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.idEndereco = idEndereco;
    }


    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(long idEndereco) {
        this.idEndereco = idEndereco;
    }
}
