package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.fiap.medibox.model.TimeLineModel;

@Dao
public interface TimeLineDao {
    @Query("SELECT * FROM tb_timeLine")
    List<TimeLineModel> getAll();

    @Query("SELECT * FROM tb_timeLine WHERE idTimeLine LIKE :id LIMIT 1")
    TimeLineModel findById(long id);

    @Query("SELECT * FROM tb_timeLine WHERE idCliente LIKE :id ")
    List<TimeLineModel> getByIdCliente(long id);

    @Query("SELECT * FROM tb_timeLine WHERE idResidenteMedicamento LIKE :id ")
    List<TimeLineModel> getByIdResidenteMedicamento(long id);

    @Insert
    void insertAll(List<TimeLineModel> list);

    @Insert
    void insert(TimeLineModel timeLineModel);

    @Update
    void update(TimeLineModel timeLineModel);

    @Delete
    void delete(TimeLineModel timeLineModel);
}
