package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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
    private List<TimeLineModel> list;


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
            });
            Call<TimeLineModel> call = timeLineService.save(model);
            call.enqueue(new Callback<TimeLineModel>() {
                @Override
                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            timeLineDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        timeLineDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (TimeLineModel model){
        TimeLineModel modelAnterior = timeLineDao.getById(model.getIdTimeLine());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                timeLineDao.update(model);
            });
            Call<TimeLineModel> call = timeLineService.update(model.getIdTimeLine(),model);
            call.enqueue(new Callback<TimeLineModel>() {
                @Override
                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            timeLineDao.update(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        timeLineDao.update(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
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

    public List<TimeLineModel> getList(){
        try{
            Call<List<TimeLineModel>> call = timeLineService.findAll();
            call.enqueue(new Callback<List<TimeLineModel>>() {
                @Override
                public void onResponse(Call<List<TimeLineModel>> call, Response<List<TimeLineModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        for (int i = 0; i<list.size(); i++){
                            TimeLineModel timeLine = list.get(i);
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





    public List<TimeLineModel> getListHoje(String date){
        try{
            timeLineDao.getByDate(date);
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }




}