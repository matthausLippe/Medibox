package br.com.fiap.medibox.data;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                                , MyDataBase.class, "MyDataBase")
                                .addCallback(sRoomDatabaseCallback)
                                .build();
                    }
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        Date data1;
        Date data2;
        Date data3;
        Date data4;
        Date data5;
        Date data6;

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ClienteDao clienteDao = INSTANCE.clienteDao();
                EnderecoDao enderecoDao = INSTANCE.enderecoDao();
                CaixaDao caixaDao = INSTANCE.caixaDao();
                GavetaDao gavetaDao = INSTANCE.gavetaDao();
                MedicamentoDao medicamentoDao = INSTANCE.medicamentoDao();
                ResidenteDao residenteDao = INSTANCE.residenteDao();
                ResidenteMedicamentoDao residenteMedicamentoDao = INSTANCE.residenteMedicamentoDao();
                TimeLineDao timeLineDao = INSTANCE.timeLineDao();

                timeLineDao.deleteAll();
                residenteMedicamentoDao.deleteAll();
                medicamentoDao.deleteAll();
                residenteDao.deleteAll();
                medicamentoDao.deleteAll();
                gavetaDao.deleteAll();
                caixaDao.deleteAll();
                enderecoDao.deleteAll();
                clienteDao.deleteAll();
                ClienteModel clienteModel = new ClienteModel(
                        1,
                        "Casa de Repouso Felita",
                        "03709977000170",
                        "info@felita.org.br",
                        "felita123",
                        "01151832874");
                clienteDao.insert(clienteModel);
                EnderecoModel enderecoModel = new EnderecoModel(1,
                        1, "CHACARA SANTO ANTONIO",
                        "488",
                        "CASA",
                        "VERBO DIVINO",
                        "SAO PAULO",
                        "SAO PAULO",
                        "04719-001");
                enderecoDao.insert(enderecoModel);
                CaixaModel caixaModel = new CaixaModel(
                        1,
                        1,
                        "00:18:A9:FB:C2:59",
                        1);
                caixaDao.insert(caixaModel);
                List<GavetaModel> listGaveta = new ArrayList<GavetaModel>();
                listGaveta.add(new GavetaModel(1, 1, 18.0, "1A", 1));
                listGaveta.add(new GavetaModel(2, 1, 17.0, "1B", 1));
                listGaveta.add(new GavetaModel(3, 1, 18.0, "1C", 1));
                listGaveta.add(new GavetaModel(4, 1, 17.0, "1D", 1));
                listGaveta.add(new GavetaModel(5, 1, 18.0, "1E", 1));
                gavetaDao.insertAll(listGaveta);

                List<MedicamentoModel> listMedicamento = new ArrayList<MedicamentoModel>();
                listMedicamento.add(new MedicamentoModel(1, "Tratamento a hipertensão", "25mg", "Laboratórios Pfizer Ltda", "Aldazida", 1));
                listMedicamento.add(new MedicamentoModel(2, "Tratamento a Diabetes", "1mg", "Grupo EMS", "Repaglinida", 2));
                listMedicamento.add(new MedicamentoModel(3, "Tratamento a hipertensão", "5mg", "Laboratórios Servier do Brasil Ltda", "Acertalix", 3));
                listMedicamento.add(new MedicamentoModel(4, "Tratamento a Ansiedade", "25mg", "Laboratórios Servier do Brasil Ltda", "Agomelatina", 4));
                listMedicamento.add(new MedicamentoModel(5, "Tratamento ao Alzheimer", "10mg", "EUROFARMA LABORATORIOS S.A.", "cloridrato de donepezila", 5));
                medicamentoDao.insertAll(listMedicamento);
                DateFormat dataNascimentoFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd@HH:mm");
                List<ResidenteModel> listaResidente = new ArrayList<ResidenteModel>();
                try {
                    data1 = new Date(dataNascimentoFormat.parse("1950-01-01").getTime());
                    data2 = new Date(dataNascimentoFormat.parse("1935-01-05").getTime());
                    data3 = new Date(dataNascimentoFormat.parse("1953-08-22").getTime());
                    data4 = new Date(dataNascimentoFormat.parse("1947-12-30").getTime());
                    data5 = new Date(dataNascimentoFormat.parse("1956-05-27").getTime());
                    data6 = new Date(dataFormat.parse("2020-10-14@12:30").getTime());
                    listaResidente.add(new ResidenteModel(1, data1, "MARIA PEREIRA RIBEIRO",  "BARBARA PEREIRA LOPES", "Tratamento a hipertensão", "1" , "F" ,"011956871359",1));
                    listaResidente.add(new ResidenteModel(2, data2, "JOSE DA SILVA CAMARGO","CAMILA RAMOS CAMARGO", "Tratamento a Diabetes", "2", "M", "011963284967", 1));
                    listaResidente.add(new ResidenteModel(3, data3, "JOAQUIM ANTUNES DE SOUSA", "JOAO ALMEIDA SOUSA", "Tratamento a hipertensão", "3", "M", "011952165988", 1));
                    listaResidente.add(new ResidenteModel(4, data4, "FRANCISCO ARAUJO GARCIA", "MIGUEL GOMES GARCIA", "Tratamento a Ansiedade", "4", "M", "011923477864", 1));
                    listaResidente.add(new ResidenteModel(5, data5, "JULIA CAMPOS CARDOSO", "ANA CAMPOS FERNANDES", "Tratamento ao Alzheimer", "5", "F", "011914788596", 1));
                    residenteDao.insertAll(listaResidente);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ResidenteMedicamentoModel residenteMedicamentoModel = new ResidenteMedicamentoModel(
                        1, data6, "200mg",20 ,8 , 1, 1, 1);
                residenteMedicamentoDao.insert(residenteMedicamentoModel);
            });
        }
    };
}
