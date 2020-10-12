package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.ClienteModel;

@Dao
public interface ClienteDao {

    @Query("SELECT * FROM tb_cliente ")
    List<ClienteModel>getAll();

    @Query("SELECT * FROM tb_cliente WHERE id LIKE :id LIMIT 1")
    ClienteModel getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ClienteModel> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ClienteModel clienteModel);

    @Update
    void update(ClienteModel clienteModel);

    @Delete
    void delete(ClienteModel clienteModel);

    @Query("DELETE FROM tb_cliente")
    void deleteAll();
}
