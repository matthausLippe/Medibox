package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.List;

@Entity(tableName = "tb_residente", foreignKeys = {
        @ForeignKey(entity = ClienteModel.class, parentColumns = "id", childColumns = "idCliente", onDelete = ForeignKey.CASCADE)
})
public class ResidenteModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idResidente")
    private long idResidente;

    @ColumnInfo(name = "idCliente")
    @SerializedName("clienteModel")
    private long idCliente;

    @ColumnInfo(name = "nomeResidente")
    private String nomeResidente;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ColumnInfo(name = "dataNascimento")
    private Date dataNascimento;

    @ColumnInfo(name = "sexo")
    private String sexo;

    @ColumnInfo(name = "nomeResponsavel")
    private String nomeResponsavel;

    @ColumnInfo(name = "telResponsavel")
    private String telResponsavel;

    @ColumnInfo(name = "quarto")
    private String quarto;

    @ColumnInfo(name = "observacoes")
    private String observacoes;

    @Ignore
    @JsonIgnore
    private ClienteModel cliente;

    @Ignore
    @JsonIgnore
    private List<ResidenteMedicamentoModel> residenteMedicamento;

    public ResidenteModel() {

    }

    @Ignore
    public ResidenteModel(long idResidente, Date dataNascimento, String nomeResidente, String nomeResponsavel, String observacoes, String quarto, String sexo, String telResponsavel, long idCliente) {
        this.idResidente = idResidente;
        this.idCliente = idCliente;
        this.nomeResidente = nomeResidente;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.nomeResponsavel = nomeResponsavel;
        this.telResponsavel = telResponsavel;
        this.quarto = quarto;
        this.observacoes = observacoes;
    }

    public long getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(long idResidente) {
        this.idResidente = idResidente;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel clienteModel) {
        this.cliente = clienteModel;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getId() {
        return idResidente;
    }

    public void setId(long id) {
        this.idResidente = id;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public String getQuarto() {
        return quarto;
    }

    public void setQuarto(String quarto) {
        this.quarto = quarto;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<ResidenteMedicamentoModel> getResidenteMedicamento() {
        return residenteMedicamento;
    }

    public void setResidenteMedicamento(List<ResidenteMedicamentoModel> residenteMedicamento) {
        this.residenteMedicamento = residenteMedicamento;
    }
}
