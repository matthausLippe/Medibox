package br.com.fiap.medibox.model;

public class GavetaModel {
	
	private long idGaveta;
	private long idMedicamento;
	private long idCaixa;
	private double temperatura;
	private String nomeGaveta;
		
	
	public GavetaModel(long idGaveta, long idMedicamento, long idCaixa, double temperatura, String nomeGaveta) {
		super();
		this.idGaveta = idGaveta;
		this.idMedicamento = idMedicamento;
		this.idCaixa = idCaixa;
		this.temperatura = temperatura;
		this.nomeGaveta = nomeGaveta;
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
