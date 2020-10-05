package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_caixa")
public class CaixaModel {

	@PrimaryKey()
	private long idCaixa;

	@ForeignKey(entity = ClienteModel.class, parentColumns = "idCliente", childColumns = "idCliente")
	@ColumnInfo(name = "idCliente")
	private long idCliente;

	@ColumnInfo(name = "mac")
	private String mac;

	@ColumnInfo(name = "status")
	private int status;
	public static int ATIVO = 1;
	public static int INATIVO = 0;
	
	
	
	public CaixaModel(long idCliente, String mac, int status) {
		super();
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
