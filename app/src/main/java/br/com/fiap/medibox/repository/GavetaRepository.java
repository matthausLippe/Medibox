package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.GavetaDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.GavetaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GavetaRepository {
    private GavetaDao gavetaDao;

    private Context context;

    private GavetaService gavetaService;
    private GavetaModel gavetaModel;
    private List<GavetaModel> list = new ArrayList<GavetaModel>();

    GavetaRepository(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        gavetaDao = db.gavetaDao();
        gavetaService = APIUtils.getGavetaService();
        context = application.getApplicationContext();
    }

    public void insert(GavetaModel model){
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                gavetaDao.insert(model);
            });
            Call<GavetaModel> call = gavetaService.save(model);
            call.enqueue(new Callback<GavetaModel>() {
                @Override
                public void onResponse(Call<GavetaModel> call, Response<GavetaModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            gavetaDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<GavetaModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        gavetaDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (GavetaModel model){
        GavetaModel modelAnterior = gavetaDao.getById(model.getIdGaveta());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                gavetaDao.update(model);
            });
            Call<GavetaModel> call = gavetaService.update(model.getIdGaveta(),model);
            call.enqueue(new Callback<GavetaModel>() {
                @Override
                public void onResponse(Call<GavetaModel> call, Response<GavetaModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            gavetaDao.update(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<GavetaModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        gavetaDao.update(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                gavetaDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(GavetaModel model){
        GavetaModel modelAnterior = gavetaDao.getById(model.getIdGaveta());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                gavetaDao.delete(model);
            });
            Call<GavetaModel> call = gavetaService.delete(model.getIdGaveta());
            call.enqueue(new Callback<GavetaModel>() {
                @Override
                public void onResponse(Call<GavetaModel> call, Response<GavetaModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            gavetaDao.insert(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<GavetaModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        gavetaDao.insert(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                gavetaDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public GavetaModel getById(long id){
        try{
            Call<GavetaModel> call = gavetaService.findById(id);
            call.enqueue(new Callback<GavetaModel>() {
                @Override
                public void onResponse(Call<GavetaModel> call, Response<GavetaModel> response) {
                    if(response.isSuccessful()){
                        gavetaModel = response.body();
                        if(gavetaModel.getIdGaveta() == gavetaDao.getById(gavetaModel.getIdGaveta()).getIdGaveta()){
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                gavetaDao.update(gavetaModel);
                            });
                        }else {
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                gavetaDao.insert(gavetaModel);
                            });
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<GavetaModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return gavetaModel;
    }

    public List<GavetaModel> getList(){
        try{
            Call<List<GavetaModel>> call = gavetaService.findAll();
            call.enqueue(new Callback<List<GavetaModel>>() {
                @Override
                public void onResponse(Call<List<GavetaModel>> call, Response<List<GavetaModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        for (int i = 0; i<list.size(); i++){
                            GavetaModel gaveta = list.get(i);
                            if(gaveta.getIdGaveta() == gavetaDao.getById(gaveta.getIdGaveta()).getIdGaveta()){
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    gavetaDao.update(gaveta);
                                });
                            }else {
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    gavetaDao.insert(gaveta);
                                });
                            }
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<GavetaModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}