package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_endereco")
public class EnderecoModel {

    @PrimaryKey(autoGenerate = true)
    private long idEndereco;


    @ColumnInfo(name = "idCliente")
    @SerializedName("clienteModel")
    private long idCliente;

    @ColumnInfo(name = "rua")
    private String rua;

    @ColumnInfo(name = "numero")
    private String numero;

    @ColumnInfo(name = "complemento")
    private String complemento;

    @ColumnInfo(name = "bairro")
    private String bairro;

    @ColumnInfo(name = "cidade")
    private String cidade;

    @ColumnInfo(name = "estado")
    private String estado;

    @ColumnInfo(name = "cep")
    private String cep;

    @Ignore
    private ClienteModel cliente;


    public EnderecoModel() {

    }

    @Ignore
    public EnderecoModel(long idEndereco, long idCliente, String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.idEndereco = idEndereco;
        this.idCliente = idCliente;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
