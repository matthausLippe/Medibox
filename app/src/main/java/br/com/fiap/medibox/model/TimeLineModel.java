package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "tb_timeLine")
public class TimeLineModel {

    @PrimaryKey()
    private long idTimeLine;

    @ForeignKey(entity = ResidenteMedicamentoModel.class, parentColumns = "idResidenteMedicamento", childColumns = "idResidenteMedicamento")
    @ColumnInfo(name = "idResidenteMedicamento")
    private long idResidenteMedicamento;

    @ForeignKey(entity = ClienteModel.class, parentColumns = "idCliente", childColumns = "idCliente")
    @ColumnInfo(name = "idCliente")
    private long idCliente;

    @ColumnInfo(name = "dataHoraMedicacao")
    private Timestamp dataHoraMedicacao;

    @ColumnInfo(name = "status")
    private int status ;


    public static int MEDICADO = 1;
    public static int NAO_MEDICADO = 0;

    public TimeLineModel(long idTimeLine, long idResidenteMedicamento, long idCliente, Timestamp dataHoraMedicacao, int status) {
        this.idTimeLine = idTimeLine;
        this.idResidenteMedicamento = idResidenteMedicamento;
        this.idCliente = idCliente;
        this.dataHoraMedicacao = dataHoraMedicacao;
        this.status = status;
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

    public Timestamp getDataHoraMedicacao() {
        return dataHoraMedicacao;
    }

    public void setDataHoraMedicacao(Timestamp dataHoraMedicacao) {
        this.dataHoraMedicacao = dataHoraMedicacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
