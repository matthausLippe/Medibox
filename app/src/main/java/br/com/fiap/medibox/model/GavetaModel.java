package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_gaveta", foreignKeys = {
        @ForeignKey(entity = CaixaModel.class, parentColumns = "idCaixa", childColumns = "idCaixa")
})
public class GavetaModel {

    @PrimaryKey()
    private long idGaveta;


    @ColumnInfo(name = "idCaixa")
    @SerializedName("caixaModel")
    private long idCaixa;

    @ColumnInfo(name = "temperatura")
    private double temperatura;

    @ColumnInfo(name = "nomeGaveta")
    private String nomeGaveta;

    @ColumnInfo(name = "situacaoGaveta")
    private int situacaoGaveta;

    @Ignore
    private CaixaModel caixa;

    public static int ATIVA = 1;
    public static int INATIVA = 0;


    public GavetaModel() {

    }

    public GavetaModel(long idGaveta, long idCaixa, double temperatura, String nomeGaveta, int situacaoGaveta) {
        this.idGaveta = idGaveta;
        this.idCaixa = idCaixa;
        this.temperatura = temperatura;
        this.nomeGaveta = nomeGaveta;
        this.situacaoGaveta = situacaoGaveta;
    }

    @Ignore


    public CaixaModel getCaixa() {
        return caixa;
    }

    public void setCaixa(CaixaModel caixa) {
        this.caixa = caixa;
    }

    public long getIdGaveta() {
        return idGaveta;
    }

    public void setIdGaveta(long idGaveta) {
        this.idGaveta = idGaveta;
    }

    public long getIdCaixa() {
        return idCaixa;
    }

    public void setIdCaixa(long idCaixa) {
        this.idCaixa = idCaixa;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public String getNomeGaveta() {
        return nomeGaveta;
    }

    public void setNomeGaveta(String nomeGaveta) {
        this.nomeGaveta = nomeGaveta;
    }

    public int getSituacaoGaveta() {
        return situacaoGaveta;
    }

    public void setSituacaoGaveta(int situacaoGaveta) {
        this.situacaoGaveta = situacaoGaveta;
    }
}
