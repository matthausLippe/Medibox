package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;

@Entity(tableName = "tb_timeLine", foreignKeys = {
        @ForeignKey(entity = ResidenteMedicamentoModel.class, parentColumns = "idResidenteMedicamento", childColumns = "idResidenteMedicamento", onDelete = ForeignKey.CASCADE)
})
public class TimeLineModel {

    @PrimaryKey(autoGenerate = true)
    private long idTimeLine;


    @ColumnInfo(name = "idResidenteMedicamento")
    @SerializedName("residenteMedicamentoModel")
    private long idResidenteMedicamento;


    @ColumnInfo(name = "idCliente")
    @SerializedName("clienteModel")
    private long idCliente;

    @ColumnInfo(name = "dataHoraMedicacao")
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
    private Date dataHoraMedicacao;

    @ColumnInfo(name = "status")
    private int status;

    @Ignore
    private ClienteModel cliente;

    @Ignore
    private ResidenteModel residente;

    @Ignore
    private ResidenteMedicamentoModel residenteMedicamento;

    @Ignore
    private MedicamentoModel medicamentoModel;

    public static int MEDICADO = 1;
    public static int NAO_MEDICADO = 0;

    public TimeLineModel() {

    }

    @Ignore
    public TimeLineModel(long idResidenteMedicamento,Date dataHoraMedicacao, long idCliente,  int status) {
        this.idResidenteMedicamento = idResidenteMedicamento;
        this.idCliente = idCliente;
        this.dataHoraMedicacao = dataHoraMedicacao;
        this.status = status;
    }

    public MedicamentoModel getMedicamentoModel() {
        return medicamentoModel;
    }

    public void setMedicamentoModel(MedicamentoModel medicamentoModel) {
        this.medicamentoModel = medicamentoModel;
    }

    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
    }

    public ResidenteModel getResidente() {
        return residente;
    }

    public void setResidente(ResidenteModel residente) {
        this.residente = residente;
    }

    public ResidenteMedicamentoModel getResidenteMedicamento() {
        return residenteMedicamento;
    }

    public void setResidenteMedicamento(ResidenteMedicamentoModel residenteMedicamento) {
        this.residenteMedicamento = residenteMedicamento;
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
