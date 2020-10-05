package br.com.fiap.medibox.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.CaixaModel;

@Dao
public interface CaixaDao {
    @Query("SELECT * FROM tb_caixa ")
    LiveData<List<CaixaModel>> getAll();

    @Query("SELECT * FROM tb_caixa WHERE idCaixa LIKE :id LIMIT 1")
    LiveData<CaixaModel> getById(long id);

    @Query("SELECT * FROM tb_caixa WHERE idCliente LIKE :id ")
    LiveData<List<CaixaModel>> getByIdCliente(long id);

    @Insert
    void insertAll(List<CaixaModel> listCaixa);

    @Insert
    void insert(CaixaModel caixaModel);

    @Update
    void update(CaixaModel caixaModel);

    @Delete
    void delete(CaixaModel caixaModel);
}
