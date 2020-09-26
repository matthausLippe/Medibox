package br.com.fiap.medibox.model;

import java.sql.Date;
import java.sql.Time;

public class TimeLineModel {
    private long idTimeLine;
    private long idResidenteMedicamento;
    private long idCliente;
    private Date dataMedicacao;
    private Time horaMedicacao;
    private int status ;
    public static int MEDICADO = 1;
    public static int NAO_MEDICADO = 0;

    public TimeLineModel(long idTimeLine, long idResidenteMedicamento, long idCliente, Date dataMedicacao, Time horaMedicacao, int status) {
        this.idTimeLine = idTimeLine;
        this.idResidenteMedicamento = idResidenteMedicamento;
        this.idCliente = idCliente;
        this.dataMedicacao = dataMedicacao;
        this.horaMedicacao = horaMedicacao;
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

    public Date getDataMedicacao() {
        return dataMedicacao;
    }

    public void setDataMedicacao(Date dataMedicacao) {
        this.dataMedicacao = dataMedicacao;
    }

    public Time getHoraMedicacao() {
        return horaMedicacao;
    }

    public void setHoraMedicacao(Time horaMedicacao) {
        this.horaMedicacao = horaMedicacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
