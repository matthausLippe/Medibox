package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_endereco")
public class EnderecoModel {

    @PrimaryKey()
    private long idEndereco;

    @ForeignKey(entity = ClienteModel.class, parentColumns = "idCliente", childColumns = "idCliente")
    @ColumnInfo(name = "idCliente")
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



    public EnderecoModel(long idCliente, String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.idCliente = idCliente;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public EnderecoModel(String numero, String bairro, String cidade, String estado, String cep) {
        this.idEndereco = idEndereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
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
