package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

@Entity(tableName = "tb_residenteMedicamento")
public class ResidenteMedicamentoModel {

    @PrimaryKey
    private long idResidenteMedicamento;

    @ForeignKey(entity = ResidenteModel.class, parentColumns = "idResidente", childColumns = "idResidente")
    @ColumnInfo(name = "idResidente")
    private long idResidente;

    @ForeignKey(entity = MedicamentoModel.class, parentColumns = "idMedicamento", childColumns = "idMedicamento")
    @ColumnInfo(name = "idMedicamento")
    private long idMedicamento;

    @ForeignKey(entity = ClienteModel.class, parentColumns = "idCliente", childColumns = "idCliente")
    @ColumnInfo(name = "idCliente")
    private long idCliente;

    @ForeignKey(entity = GavetaModel.class, parentColumns = "idGaveta", childColumns = "idGaveta")
    @ColumnInfo(name = "idGaveta")
    private long idGaveta;

    @ColumnInfo(name = "dosagem")
    private String dosagem;

    @ColumnInfo(name = "intervalo")
    private double intervalo;

    @ColumnInfo(name = "dataHoraInicio")
    private Timestamp dataHoraInicio;

    @ColumnInfo(name = "doses")
    private int doses;


    public ResidenteMedicamentoModel(long idResidente, long idMedicamento, long idCliente,
                                     long idGaveta, String dosagem, double intervalo, Timestamp dataHoraInicio, int doses) {

        this.idResidente = idResidente;
        this.idMedicamento = idMedicamento;
        this.idCliente = idCliente;
        this.idGaveta = idGaveta;
        this.dosagem = dosagem;
        this.intervalo = intervalo;
        this.dataHoraInicio = dataHoraInicio;
        this.doses = doses;

    }


    public long getIdResidenteMedicamento() {
        return idResidenteMedicamento;
    }

    public void setIdResidenteMedicamento(long idResidenteMedicamento) {
        this.idResidenteMedicamento = idResidenteMedicamento;
    }

    public long getIdResidente() {
        return idResidente;
    }

    public void setIdResidente(long idResidente) {
        this.idResidente = idResidente;
    }

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getIdGaveta() {
        return idGaveta;
    }

    public void setIdGaveta(long idGaveta) {
        this.idGaveta = idGaveta;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public double getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(double intervalo) {
        this.intervalo = intervalo;
    }

    public Timestamp getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Timestamp dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }


}
