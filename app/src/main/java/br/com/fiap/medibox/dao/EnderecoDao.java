package br.com.fiap.medibox.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.EnderecoModel;

@Dao
public interface EnderecoDao {

    @Query("SELECT * FROM tb_endereco ")
    LiveData<List<EnderecoModel>> getAll();

    @Query("SELECT * FROM tb_endereco WHERE idEndereco LIKE :id LIMIT 1")
    LiveData<EnderecoModel> getById(long id);

    @Query("SELECT * FROM tb_endereco WHERE idCliente LIKE :id ")
    LiveData<List<EnderecoModel>> getByIdCliente(long id);

    @Insert
    void insertAll(List<EnderecoModel> list);

    @Insert
    void insert(EnderecoModel enderecoModel);

    @Update
    void update(EnderecoModel enderecoModel);

    @Delete
    void delete(EnderecoModel enderecoModel);


}
