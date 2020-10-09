package br.com.fiap.medibox.data;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.fiap.medibox.dao.CaixaDao;
import br.com.fiap.medibox.dao.ClienteDao;
import br.com.fiap.medibox.dao.EnderecoDao;
import br.com.fiap.medibox.dao.GavetaDao;
import br.com.fiap.medibox.dao.MedicamentoDao;
import br.com.fiap.medibox.dao.ResidenteDao;
import br.com.fiap.medibox.dao.ResidenteMedicamentoDao;
import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.model.CaixaModel;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.Converter;
import br.com.fiap.medibox.model.EnderecoModel;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;

@Database(entities = {CaixaModel.class, ClienteModel.class, EnderecoModel.class, GavetaModel.class
        , MedicamentoModel.class, ResidenteMedicamentoModel.class, ResidenteModel.class
        , TimeLineModel.class} ,version = 1, exportSchema = false)
@TypeConverters(Converter.class)
public abstract class MyDataBase extends RoomDatabase {

    public abstract CaixaDao caixaDao();

    public abstract ClienteDao clienteDao();

    public abstract EnderecoDao enderecoDao();

    public abstract GavetaDao gavetaDao();

    public abstract MedicamentoDao medicamentoDao();

    public abstract ResidenteDao residenteDao();

    public abstract ResidenteMedicamentoDao residenteMedicamentoDao();

    public abstract TimeLineDao timeLineDao();

    private static volatile MyDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                synchronized (MyDataBase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                MyDataBase.class, "MyDataBase")
                                .build();
                    }
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {

            });
        }
    };
}
