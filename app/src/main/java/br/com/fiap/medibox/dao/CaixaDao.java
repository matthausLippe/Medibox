package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.CaixaModel;

@Dao
public interface CaixaDao {
    @Query("SELECT * FROM tb_caixa ")
    List<CaixaModel> getAll();

    @Query("SELECT * FROM tb_caixa WHERE idCaixa LIKE :id LIMIT 1")
    CaixaModel getById(long id);

    @Query("SELECT * FROM tb_caixa WHERE idCliente LIKE :id ")
    List<CaixaModel> getByIdCliente(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CaixaModel> listCaixa);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CaixaModel caixaModel);

    @Update
    void update(CaixaModel caixaModel);

    @Delete
    void delete(CaixaModel caixaModel);

    @Query("DELETE FROM tb_caixa")
    void deleteAll();
}
