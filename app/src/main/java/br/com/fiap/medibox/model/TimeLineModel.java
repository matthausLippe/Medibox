package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

@Entity(tableName = "tb_timeLine")
public class TimeLineModel {

    @PrimaryKey()
    private long idTimeLine;

    @ForeignKey(entity = ResidenteMedicamentoModel.class, parentColumns = "idResidenteMedicamento", childColumns = "idResidenteMedicamento")
    @ColumnInfo(name = "idResidenteMedicamento")
    private long idResidenteMedicamento;

    @ForeignKey(entity = ClienteModel.class, parentColumns = "idCliente", childColumns = "idCliente")
    @ColumnInfo(name = "idCliente")
    @SerializedName("clienteModel")
    private long idCliente;

    @ColumnInfo(name = "dataHoraMedicacao")
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
    private Date dataHoraMedicacao;

    @ColumnInfo(name = "status")
    private int status;

    public static int MEDICADO = 1;
    public static int NAO_MEDICADO = 0;

    @Ignore
    private ClienteModel clienteModel;

    @Ignore
    private ResidenteModel residenteModel;

    public TimeLineModel() {

    }

    @Ignore
    public TimeLineModel(long idResidenteMedicamento, long idCliente, Date dataHoraMedicacao, int status) {
        this.idResidenteMedicamento = idResidenteMedicamento;
        this.idCliente = idCliente;
        this.dataHoraMedicacao = dataHoraMedicacao;
        this.status = status;
    }

    public ClienteModel getClienteModel() {
        return clienteModel;
    }

    public void setClienteModel(ClienteModel clienteModel) {
        this.clienteModel = clienteModel;
    }

    public ResidenteModel getResidenteModel() {
        return residenteModel;
    }

    public void setResidenteModel(ResidenteModel residenteModel) {
        this.residenteModel = residenteModel;
    }

    public long getIdTimeLine() {
        return idTimeLine;
    }

    public void setIdTimeLine(long idTimeLine) {
        this.idTimeLine = idTimeLine;
    }

    public long getIdResidenteMedicamento() {
        return idResidenteMedicamento;
    }

    public void setIdResidenteMedicamento(long idResidenteMedicamento) {
        this.idResidenteMedicamento = idResidenteMedicamento;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public Date getDataHoraMedicacao() {
        return dataHoraMedicacao;
    }

    public void setDataHoraMedicacao(Date dataHoraMedicacao) {
        this.dataHoraMedicacao = dataHoraMedicacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
