package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_medicamento")
public class MedicamentoModel {

    @PrimaryKey()
    private long idMedicamento;

    @ColumnInfo(name = "nomeMedicamento")
    private String nomeMedicamento;

    @ColumnInfo(name = "laboratorio")
    private String laboratorio;

    @ForeignKey(entity = GavetaModel.class, parentColumns = "idGaveta", childColumns = "idGaveta")
    @ColumnInfo(name = "idGaveta")
    private long idGaveta;

    @ColumnInfo(name = "dosagem")
    private String dosagem;

    @ColumnInfo(name = "descricao")
    private String descricao;


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
