package br.com.fiap.medibox.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;
import java.util.List;

import br.com.fiap.medibox.model.TimeLineModel;

@Dao
public interface TimeLineDao {
    @Query("SELECT * FROM tb_timeLine")
    List<TimeLineModel> getAll();

    @Query("SELECT * FROM tb_timeLine WHERE idTimeLine LIKE :id LIMIT 1")
    TimeLineModel getById(long id);

    @Query("SELECT * FROM tb_timeLine WHERE idCliente LIKE :id ")
    List<TimeLineModel> getByIdCliente(long id);

    @Query("SELECT * FROM tb_timeLine WHERE idResidenteMedicamento LIKE :id ")
    List<TimeLineModel> getByIdResidenteMedicamento(long id);

    @Query("SELECT * FROM tb_timeLine WHERE dataHoraMedicacao LIKE :data ORDER BY dataHoraMedicacao ")
    List<TimeLineModel> getByDate(Date data); //AAAAMMdd

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TimeLineModel> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TimeLineModel timeLineModel);

    @Update
    void update(TimeLineModel timeLineModel);

    @Delete
    void delete(TimeLineModel timeLineModel);

    @Query("DELETE FROM tb_timeLine")
    void deleteAll();

    @Query("UPDATE tb_timeLine SET status = 1 WHERE idTimeLine LIKE :id")
    void medicar(long id);
}
