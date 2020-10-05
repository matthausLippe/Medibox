package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.util.ArrayList;

@Entity(tableName = "tb_residente")
public class ResidenteModel {

    @PrimaryKey()
    private long idResidente;

    @ForeignKey(entity = ClienteModel.class, parentColumns = "idCliente", childColumns = "idCliente")
    @ColumnInfo(name = "idCliente")
    private long idCliente;

    @ColumnInfo(name = "nomeResidente")
    private String nomeResidente;

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


    private ArrayList<ResidenteMedicamentoModel> residenteMedicamento;


    public ResidenteModel(String nomeResidente, Date dataNascimento, String sexo, String nomeResponsavel, String telResponsavel, String quarto, String observacoes) {
        this.nomeResidente = nomeResidente;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.nomeResponsavel = nomeResponsavel;
        this.telResponsavel = telResponsavel;
        this.quarto = quarto;
        this.observacoes = observacoes;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(long idResidente) {
        this.idResidente = idResidente;
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

    public ArrayList<ResidenteMedicamentoModel> getResidenteMedicamento() {
        return residenteMedicamento;
    }

    public void setResidenteMedicamento(ArrayList<ResidenteMedicamentoModel> residenteMedicamento) {
        this.residenteMedicamento = residenteMedicamento;
    }
}
