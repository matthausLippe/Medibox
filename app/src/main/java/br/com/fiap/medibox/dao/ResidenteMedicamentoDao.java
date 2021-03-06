package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.ResidenteMedicamentoModel;

@Dao
public interface ResidenteMedicamentoDao {

    @Query("SELECT * FROM tb_residenteMedicamento")
    List<ResidenteMedicamentoModel> getAll();

    @Query("SELECT * FROM tb_residenteMedicamento WHERE idResidente like :id")
    List<ResidenteMedicamentoModel> getAllByIdResidente(long id);

    @Query("SELECT * FROM tb_residenteMedicamento WHERE idResidenteMedicamento LIKE :id LIMIT 1")
    ResidenteMedicamentoModel getById(long id);

    @Query("SELECT * FROM tb_residenteMedicamento WHERE idCliente LIKE :id ")
    List<ResidenteMedicamentoModel> getByIdCliente(long id);

    @Query("SELECT * FROM tb_residenteMedicamento WHERE idMedicamento LIKE :id ")
    List<ResidenteMedicamentoModel> getByIdMedicamento(long id);

    @Query("SELECT * FROM tb_residenteMedicamento WHERE idResidente LIKE :id ")
    List<ResidenteMedicamentoModel> getByIdResidente(long id);

    @Query("SELECT * FROM tb_residenteMedicamento WHERE idResidenteMedicamento LIKE :id ")
    List<ResidenteMedicamentoModel> getByIdResidenteMedicamento(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ResidenteMedicamentoModel> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(ResidenteMedicamentoModel residenteMedicamentoModel);

    @Update
    void update(ResidenteMedicamentoModel residenteMedicamentoModel);

    @Delete
    void delete(ResidenteMedicamentoModel residenteMedicamentoModel);

    @Query("DELETE FROM tb_residenteMedicamento")
    void deleteAll();
}
