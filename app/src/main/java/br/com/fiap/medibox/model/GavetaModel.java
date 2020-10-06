package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_gaveta")
public class GavetaModel {

	@PrimaryKey()
	private long idGaveta;

	@ForeignKey(entity = MedicamentoModel.class, parentColumns = "idMedicamento", childColumns = "idMedicamento")
	@ColumnInfo(name = "idMedicamento")
	private long idMedicamento;

	@ForeignKey(entity = CaixaModel.class, parentColumns = "idCaixa", childColumns = "idCaixa")
	@ColumnInfo(name = "idCaixa")
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
	public long getIdMedicamento() {
		return idMedicamento;
	}
	public void setIdMedicamento(long idMedicamento) {
		this.idMedicamento = idMedicamento;
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

	
	
	
}
