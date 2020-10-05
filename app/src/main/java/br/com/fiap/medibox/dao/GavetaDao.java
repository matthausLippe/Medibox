package br.com.fiap.medibox.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.GavetaModel;

@Dao
public interface GavetaDao {

    @Query("SELECT * FROM tb_gaveta ")
    LiveData<List<GavetaModel>> getAll();

    @Query("SELECT * FROM tb_gaveta WHERE idGaveta LIKE :id LIMIT 1")
    LiveData<GavetaModel> getById(long id);

    @Query("SELECT * FROM tb_gaveta WHERE idCaixa LIKE :id ")
    LiveData<List<GavetaModel>> getByIdCaixa(long id);

    @Query("SELECT * FROM tb_gaveta WHERE idMedicamento LIKE :id ")
    LiveData<List<GavetaModel>> getByIdMedicamento(long id);

    @Insert
    void insertAll(List<GavetaModel> list);

    @Insert
    void insert(GavetaModel gavetaModel);

    @Update
    void update(GavetaModel gavetaModel);

    @Delete
    void delete(GavetaModel gavetaModel);
}
