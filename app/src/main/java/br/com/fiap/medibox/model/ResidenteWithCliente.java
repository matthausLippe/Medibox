package br.com.fiap.medibox.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ResidenteWithCliente {
    @Embedded
    public ClienteModel clienteModel;

    @Relation(entity = ResidenteModel.class, parentColumn = "id", entityColumn = "idCliente")
    public List<ResidenteModel> residenteModel;


}
