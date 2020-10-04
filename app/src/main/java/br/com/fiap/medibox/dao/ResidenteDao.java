package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.ResidenteModel;

@Dao
public interface ResidenteDao {

    @Query("SELECT * FROM tb_residente")
    List<ResidenteModel> getAll();

    @Query("SELECT * FROM tb_residente WHERE idResidente LIKE :id LIMIT 1")
    ResidenteModel findById(long id);

    @Query("SELECT * FROM tb_residente WHERE idCliente LIKE :id ")
    List<ResidenteModel> getByIdCliente(long id);

    @Insert
    void insertAll(List<ResidenteModel> list);

    @Insert
    void insert(ResidenteModel residenteModel);

    @Update
    void update(ResidenteModel residenteModel);

    @Delete
    void delete(ResidenteModel residenteModel);
}
