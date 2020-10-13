package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_cliente")
public class ClienteModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("idCliente")
    private long id;

    @ColumnInfo(name = "nomeCliente")
    private String nomeCliente;

    @ColumnInfo(name = "cpfCnpj")
    private String cpfCnpj;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "senha")
    private String senha;

    @ColumnInfo(name = "telefone")
    private String telefone;

    public ClienteModel() {
    }

    @Ignore
    public ClienteModel(long id, String nomeCliente, String cpfCnpj, String email, String senha, String telefone) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

}
