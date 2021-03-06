package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.GavetaModel;

@Dao
public interface GavetaDao {

    @Query("SELECT * FROM tb_gaveta ")
    List<GavetaModel> getAll();

    @Query("SELECT * FROM tb_gaveta WHERE idGaveta LIKE :id LIMIT 1")
    GavetaModel getById(long id);

    @Query("SELECT * FROM tb_gaveta WHERE idCaixa LIKE :id ")
    List<GavetaModel> getByIdCaixa(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GavetaModel> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GavetaModel gavetaModel);

    @Update
    void update(GavetaModel gavetaModel);

    @Delete
    void delete(GavetaModel gavetaModel);

    @Query("DELETE FROM tb_gaveta")
    void deleteAll();
}
