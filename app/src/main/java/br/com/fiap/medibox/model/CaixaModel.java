package br.com.fiap.medibox.model;

public class CaixaModel {
	
	private long idCaixa;
	private long idCliente;
	private String mac;
	private int status;
	public static int ATIVO = 1;
	public static int INATIVO = 0;
	
	
	
	public CaixaModel(long idCaixa, long idCliente, String mac, int status) {
		super();
		this.idCaixa = idCaixa;
		this.idCliente = idCliente;
		this.mac = mac;
		this.status = status;
	}
	
	public long getIdCaixa() {
		return idCaixa;
	}
	public void setIdCaixa(long idCaixa) {
		this.idCaixa = idCaixa;
	}
	public long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
