package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.ResidenteWithCliente;

@Dao
public interface ResidenteDao {

    @Query("SELECT * FROM tb_residente")
    List<ResidenteModel> getAll();

    @Query("SELECT * FROM tb_residente WHERE idResidente LIKE :id LIMIT 1")
    ResidenteModel getById(long id);

    @Query("SELECT * FROM tb_residente WHERE idCliente LIKE :id ")
    List<ResidenteModel> getByIdCliente(long id);

    @Query("SELECT * FROM tb_residente INNER JOIN tb_cliente ON tb_residente.idCliente = tb_cliente.id WHERE tb_residente.idResidente LIKE :id")
    ResidenteWithCliente getResidenteCliente(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ResidenteModel> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ResidenteModel residenteModel);

    @Update
    void update(ResidenteModel residenteModel);

    @Delete
    void delete(ResidenteModel residenteModel);

    @Query("DELETE FROM tb_residente")
    void deleteAll();
}
