package br.com.fiap.medibox.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_caixa", foreignKeys = {
        @ForeignKey(entity = ClienteModel.class, parentColumns = "id", childColumns = "idCliente", onDelete = ForeignKey.CASCADE)
})
public class CaixaModel {

    @PrimaryKey(autoGenerate = true)
    private long idCaixa;

    @ColumnInfo(name = "idCliente")
    @SerializedName("clienteModel")
    private long idCliente;

    @ColumnInfo(name = "mac")
    private String mac;

    @ColumnInfo(name = "status")
    private int status;

    @Ignore
    private ClienteModel cliente;

    public static int ATIVO = 1;
    public static int INATIVO = 0;

    public CaixaModel() {

    }

    public CaixaModel(long idCaixa, long idCliente, String mac, int status) {
        this.idCaixa = idCaixa;
        this.idCliente = idCliente;
        this.mac = mac;
        this.status = status;
    }

    @Ignore


    public ClienteModel getCliente() {
        return cliente;
    }

    public void setCliente(ClienteModel cliente) {
        this.cliente = cliente;
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
