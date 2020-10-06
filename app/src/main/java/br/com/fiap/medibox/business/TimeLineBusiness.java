package br.com.fiap.medibox.business;

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


public class TimeLineBusiness {
    private SimpleDateFormat formataData = new SimpleDateFormat("yyyyMMdd");

    private Context context;

    private TimeLineDao timeLineDao;
    private TimeLineService timeLineService;
    private TimeLineModel timeLineModel;
    private List<TimeLineModel> list;


    public TimeLineBusiness(Application application) {
        MyDataBase db = MyDataBase.getDatabase(application);
        timeLineDao = db.timeLineDao();
        context = application.getApplicationContext();
        timeLineService = APIUtils.getTimeLineService();
    }

    public void insert (TimeLineModel model){
        try{
            timeLineDao.insert(model);
            Call<TimeLineModel> call = timeLineService.save(model);
            call.enqueue(new Callback<TimeLineModel>() {
                @Override
                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        timeLineDao.delete(model);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    timeLineDao.delete(model);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            timeLineDao.delete(model);
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(TimeLineModel model){
        TimeLineModel modelAnterior = timeLineDao.getById(model.getIdTimeLine());
        try{
            Call<TimeLineModel> call = timeLineService.update(model.getIdTimeLine(), model);
            call.enqueue(new Callback<TimeLineModel>() {
                @Override
                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                    Toast.makeText(context, "Atualizado com Sucesso!", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    timeLineDao.update(modelAnterior);
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(TimeLineModel model){
       TimeLineModel modelAntigo = timeLineDao.getById(model.getIdTimeLine());
        try{
            Call<TimeLineModel> call = timeLineService.delete(model.getIdTimeLine());
            call.enqueue(new Callback<TimeLineModel>() {
                @Override
                public void onResponse(Call<TimeLineModel> call, Response<TimeLineModel> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        timeLineDao.insert(modelAntigo);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    timeLineDao.insert(modelAntigo);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            timeLineDao.insert(modelAntigo);
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
                        timeLineDao.insert(timeLineModel);
                    }else {
                        timeLineModel = timeLineDao.getById(id);
                    }
                }
                @Override
                public void onFailure(Call<TimeLineModel> call, Throwable t) {
                    timeLineModel = timeLineDao.getById(id);
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show();
        }
        return timeLineModel;
    }

    //Obter Lista TimeLine
    public List<TimeLineModel> getList(){
        try{
            Call<List<TimeLineModel>> call = timeLineService.findAll();
            call.enqueue(new Callback<List<TimeLineModel>>() {
                @Override
                public void onResponse(Call<List<TimeLineModel>> call, Response<List<TimeLineModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        timeLineDao.insertAll(list);
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