package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.TimeLineService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimeLineRepository {
    private SimpleDateFormat formataData = new SimpleDateFormat("yyyyMMdd");

    private Context context;

    private TimeLineDao timeLineDao;
    private TimeLineService timeLineService;
    private TimeLineModel timeLineModel;
    private MutableLiveData<List<TimeLineModel>> list = new MutableLiveData<>();
    private List<TimeLineModel> lista;
    private TimeLineModel modelAnterior;


    public TimeLineRepository(Application application) {
        MyDataBase db = MyDataBase.getDatabase(application);
        timeLineDao = db.timeLineDao();
        context = application.getApplicationContext();
        timeLineService = APIUtils.getTimeLineService();
    }

    public void insert(TimeLineModel model){
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                timeLineDao.insert(model);
                Log.e("TimeLineRepository","Inserido no DB");
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao inserir TimeLine no DB!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(TimeLineModel model) {
        try {
            MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    timeLineDao.insert(model);
                    Call<TimeLineModel> call = timeLineService.update(model.getIdTimeLine(), model);
                    call.enqueue(new Callback<TimeLineModel>() {
                        @Override
                        public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                            } else {
                                MyDataBase.databaseWriteExecutor.execute(() -> {
                                    timeLineDao.insert(modelAnterior);
                                });
                                Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TimeLineModel> call, Throwable t) {
                            MyDataBase.databaseWriteExecutor.execute(() -> {
                                timeLineDao.update(modelAnterior);
                            });
                            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        } catch (Exception e) {
            MyDataBase.databaseWriteExecutor.execute(() -> {
                timeLineDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateList(List<TimeLineModel> list) {
        try {
            MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    for(TimeLineModel model : list){
                        insert(model);
                    }
                }
            });
        } catch (Exception e) {
            MyDataBase.databaseWriteExecutor.execute(() -> {
                timeLineDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(TimeLineModel model){
        TimeLineModel modelAnterior = timeLineDao.getById(model.getIdTimeLine());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                timeLineDao.delete(model);
            });
            Call<TimeLineModel> call = timeLineService.delete(model.getIdTimeLine());
            call.enqueue(new Callback<TimeLineModel>() {
                @Override
                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            timeLineDao.insert(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        timeLineDao.insert(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                timeLineDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public TimeLineModel getById(long id){
        try{
            Call<TimeLineModel> call = timeLineService.findById(id);
            call.enqueue(new Callback<TimeLineModel>() {
                @Override
                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                    if(response.isSuccessful()){
                        timeLineModel = response.body();
                        if(timeLineModel.getIdTimeLine() == timeLineDao.getById(timeLineModel.getIdTimeLine()).getIdTimeLine()){
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                timeLineDao.update(timeLineModel);
                            });
                        }else {
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                timeLineDao.insert(timeLineModel);
                            });
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return timeLineModel;
    }

    public MutableLiveData<List<TimeLineModel>> getList(){
        try{
            Call<List<TimeLineModel>> call = timeLineService.findAll();
            call.enqueue(new Callback<List<TimeLineModel>>() {
                @Override
                public void onResponse(Call<List<TimeLineModel>> call, Response<List<TimeLineModel>> response) {
                    if(response.isSuccessful()){
                        list.postValue(response.body());
                        for (int i = 0; i<list.getValue().size(); i++){
                            TimeLineModel timeLine = list.getValue().get(i);
                            if(timeLine.getIdTimeLine() == timeLineDao.getById(timeLine.getIdTimeLine()).getIdTimeLine()){
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    timeLineDao.update(timeLine);
                                });
                            }else {
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    timeLineDao.insert(timeLine);
                                });
                            }
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<TimeLineModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    public MutableLiveData<List<TimeLineModel>> getListService() {
        Call<List<TimeLineModel>> call = timeLineService.findAll();
        call.enqueue(new Callback<List<TimeLineModel>>() {
            @Override
            public void onResponse(Call<List<TimeLineModel>> call, Response<List<TimeLineModel>> response) {
                if (response.isSuccessful()) {
                    list.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<TimeLineModel>> call, Throwable t) {
                Log.e("ResidenteService   ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

    public void saveDb(TimeLineModel model) {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    timeLineModel = timeLineDao.getById(model.getIdTimeLine());
                    if (timeLineModel == null) {
                        timeLineDao.insert(model);
                        Log.e("TimeLineRepository", "TimeLine: "+model.getIdTimeLine()+" inserido no DB");
                    } else {
                        timeLineDao.update(model);
                        Log.e("TimeLineRepository", "TimeLine: "+model.getIdTimeLine()+" alterado no DB");
                    }
                } catch (Exception e) {
                    Log.e("TimeLineRepository", "Falha ao realizar o insert " + e.getMessage());
                }
            }
        });
    }

    public void saveListDb(List<TimeLineModel> lista) {
        if (lista != null) {
            for(int i = 0; i<lista.size(); i++){
                TimeLineModel model = lista.get(i);
                saveDb(model);
            }
        }
    }

    public MutableLiveData<List<TimeLineModel>> getListNotification(Date date){
        try{
            MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    list.postValue(timeLineDao.getByDate(date));
                }
            });

        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    public MutableLiveData<List<TimeLineModel>> getListDb(){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list.postValue(timeLineDao.getAll());
            }
        });
        return list;
    }

    public void gerarLoteTimeLine(long idResidenteMedicamento,@NonNull List<TimeLineModel> timeLineModels){
        try {
            MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    lista = timeLineDao.getByIdResidenteMedicamento(idResidenteMedicamento); // obtem a relação do timeLine vinculado a esse idResidenteMedicamento
                    if (lista != null) {
                        for (TimeLineModel currentTimeLine : lista) {
                            if (currentTimeLine.getIdResidenteMedicamento() == idResidenteMedicamento) {
                                timeLineDao.delete(currentTimeLine); //excluido do DB todos os itens do mesmo ResidenteMedicamento para não ter conflito
                            }
                        }
                        timeLineDao.insertAll(timeLineModels); //Inseri no banco de dados a linha atualizada, após realizar a exclusão dos repetidos
                        lista = timeLineDao.getByIdResidenteMedicamento(idResidenteMedicamento);
                        Call<List<TimeLineModel>> callList = timeLineService.findAll(); //obter lista atualizada do serviço
                        callList.enqueue(new Callback<List<TimeLineModel>>() {
                            @Override
                            public void onResponse(Call<List<TimeLineModel>> call, Response<List<TimeLineModel>> response) {
                                if (response.isSuccessful()) {
                                    for (TimeLineModel currentTimeLine : response.body()) { //pegar a lista atualizada e comprar com a lista que vai para o banco de dados
                                        if (currentTimeLine.getIdResidenteMedicamento() == idResidenteMedicamento) { // realiza o filtro dos TimeLine items que pertencem a essa ResidenteMedicamento
                                            Call<TimeLineModel> deleteCall = timeLineService.delete(currentTimeLine.getIdTimeLine()); // realiza a exclusão no serviço do itens antigos desse ResidenteMedicamento
                                            deleteCall.enqueue(new Callback<TimeLineModel>() {
                                                @Override
                                                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                                                    if (response.isSuccessful()) {
                                                        Log.e("TimeLineRepository", "TimeLine ID: " + currentTimeLine.getIdTimeLine() + " excluido do Serviço, para ser atualizado");
                                                    } else {
                                                        Log.e("TimeLineRepository", "falha ao realizar exclusão de item antigo no Serviço");
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                                                    Log.e("TimeLineRepository", "falha ao realizar exclusão de item antigo no Serviço");
                                                }
                                            });
                                        }
                                    }
                                    for(TimeLineModel model : lista) {
                                        model.setIdTimeLine(0);
                                        Call<TimeLineModel> saveCall = timeLineService.save(model);
                                        saveCall.enqueue(new Callback<TimeLineModel>() {
                                            @Override
                                            public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                                                if (response.isSuccessful()) {
                                                    Log.e("TimeLineRepository", "TimeLine ID: " + model.getIdTimeLine() + " inserido no Serviço");
                                                } else {
                                                    Log.e("TimeLineRepository", "falha em salvar os dados no serviço AQUI <-- " + response.message());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<TimeLineModel> call, Throwable t) {
                                                Log.e("TimeLineRepository", "falha em salvar os dados no serviço AQUI 2" + t.getMessage());
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<TimeLineModel>> call, Throwable t) {
                                Log.e("TimeLineRepository", "falha ao realizar exclusão de item antigo no Serviço AQUI 3" + t.getMessage());
                            }
                        });
                    }
                }

            });
        }catch (SQLiteException sql){
            Log.e("TimeLineRepository","falha ao executar ação no DB "+sql.getMessage());
        }
    }




}