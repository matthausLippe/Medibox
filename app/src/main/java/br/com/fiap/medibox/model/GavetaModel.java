package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_gaveta")
public class GavetaModel {

	@PrimaryKey()
	private long idGaveta;

	@ForeignKey(entity = CaixaModel.class, parentColumns = "idCaixa", childColumns = "idCaixa")
	@ColumnInfo(name = "idCaixa")
	@SerializedName("caixaModel")
	private long idCaixa;

	@ColumnInfo(name = "temperatura")
	private double temperatura;

	@ColumnInfo(name = "nomeGaveta")
	private String nomeGaveta;

	@ColumnInfo(name = "situacaoGaveta")
	private int situacaoGaveta;

	public static int ATIVA = 1;
	public static int INATIVA = 0;
		
	
	public GavetaModel() {

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
