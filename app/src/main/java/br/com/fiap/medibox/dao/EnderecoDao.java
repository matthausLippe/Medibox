package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.EnderecoModel;

@Dao
public interface EnderecoDao {

    @Query("SELECT * FROM tb_endereco ")
    List<EnderecoModel> getAll();

    @Query("SELECT * FROM tb_endereco WHERE idEndereco LIKE :id LIMIT 1")
    EnderecoModel getById(long id);

    @Query("SELECT * FROM tb_endereco WHERE idCliente LIKE :id ")
    List<EnderecoModel> getByIdCliente(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EnderecoModel> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EnderecoModel enderecoModel);

    @Update
    void update(EnderecoModel enderecoModel);

    @Delete
    void delete(EnderecoModel enderecoModel);

    @Query("DELETE FROM tb_endereco")
    void deleteAll();


}
