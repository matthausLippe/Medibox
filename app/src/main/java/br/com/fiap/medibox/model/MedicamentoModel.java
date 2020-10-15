package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_medicamento")
public class MedicamentoModel {

    @PrimaryKey(autoGenerate = true)
    private long idMedicamento;

    @ColumnInfo(name = "nomeMedicamento")
    private String nomeMedicamento;

    @ColumnInfo(name = "laboratorio")
    private String laboratorio;


    @ColumnInfo(name = "idGaveta")
    @SerializedName("gavetaModel")
    private long idGaveta;

    @ColumnInfo(name = "dosagem")
    private String dosagem;

    @ColumnInfo(name = "descricao")
    private String descricao;

    @Ignore
    private GavetaModel gaveta;

    public MedicamentoModel() {

    }

    @Ignore
    public MedicamentoModel(long idMedicamento, String descricao, String dosagem, String laboratorio, String nomeMedicamento, long idGaveta) {
        this.idMedicamento = idMedicamento;
        this.nomeMedicamento = nomeMedicamento;
        this.laboratorio = laboratorio;
        this.idGaveta = idGaveta;
        this.dosagem = dosagem;
        this.descricao = descricao;
    }




    public GavetaModel getGaveta() {
        return gaveta;
    }

    public void setGaveta(GavetaModel gaveta) {
        this.gaveta = gaveta;
    }

    public long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public long getIdGaveta() {
        return idGaveta;
    }

    public void setIdGaveta(long idGaveta) {
        this.idGaveta = idGaveta;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
