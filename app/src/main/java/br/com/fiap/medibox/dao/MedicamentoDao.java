package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.MedicamentoModel;

@Dao
public interface MedicamentoDao {

    @Query("SELECT * FROM tb_medicamento ")
    List<MedicamentoModel> getAll();

    @Query("SELECT * FROM tb_medicamento WHERE idMedicamento LIKE :id LIMIT 1")
    MedicamentoModel getById(long id);

    @Query("SELECT * FROM tb_medicamento WHERE idGaveta LIKE :id")
    MedicamentoModel getByIdGaveta(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MedicamentoModel> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MedicamentoModel medicamentoModel);

    @Update
    void update(MedicamentoModel medicamentoModel);

    @Delete
    void delete(MedicamentoModel medicamentoModel);

    @Query("DELETE FROM tb_medicamento")
    void deleteAll();
}
