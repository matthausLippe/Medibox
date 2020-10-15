package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;


@Entity(tableName = "tb_residenteMedicamento")
public class ResidenteMedicamentoModel {

    @PrimaryKey(autoGenerate = true)
    private long idResidenteMedicamento;


    @ColumnInfo(name = "idResidente")
    @SerializedName("residenteModel")
    private long idResidente;


    @ColumnInfo(name = "idMedicamento")
    @SerializedName("medicamentoModel")
    private long idMedicamento;


    @ColumnInfo(name = "idCliente")
    @SerializedName("clienteModel")
    private long idCliente;

    @ColumnInfo(name = "dosagem")
    private String dosagem;

    @ColumnInfo(name = "intervalo")
    private double intervalo;

    @TypeConverters(Converter.class)
    @ColumnInfo(name = "dataHoraInicio")
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
    private Date dataHoraInicio;

    @ColumnInfo(name = "doses")
    private int doses;

    @Ignore
    private ClienteModel cliente;

    @Ignore
    private ResidenteModel residente;

    @Ignore
    private MedicamentoModel medicamento;

    public ResidenteMedicamentoModel() {

    }

    public ResidenteMedicamentoModel(long idResidenteMedicamento,  Date dataHoraInicio, String dosagem,  int doses,  double intervalo, long idCliente, long idResidente, long idMedicamento) {
        this.idResidenteMedicamento = idResidenteMedicamento;
        this.idResidente = idResidente;
        this.idMedicamento = idMedicamento;
        this.idCliente = idCliente;
        this.dosagem = dosagem;
        this.intervalo = intervalo;
        this.dataHoraInicio = dataHoraInicio;
        this.doses = doses;
    }

    @Ignore


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

    public MedicamentoModel getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoModel medicamento) {
        this.medicamento = medicamento;
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

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }


}
