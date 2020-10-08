package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.ResidenteDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.ResidenteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidenteRepository {

    private ResidenteDao residenteDao;

    private Context context;

    private ResidenteService residenteService;
    private ResidenteModel residenteModel;
    private List<ResidenteModel> list = new ArrayList<ResidenteModel>();

    public ResidenteRepository(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        residenteDao = db.residenteDao();
        residenteService = APIUtils.getResidenteService();
        context = application.getApplicationContext();
    }

    public void insert(ResidenteModel model){
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteDao.insert(model);
            });
            Call<ResidenteModel> call = residenteService.save(model);
            call.enqueue(new Callback<ResidenteModel>() {
                @Override
                public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            residenteDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        residenteDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (ResidenteModel model){
        ResidenteModel modelAnterior = residenteDao.getById(model.getIdResidente());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteDao.update(model);
            });
            Call<ResidenteModel> call = residenteService.update(model.getIdResidente(),model);
            call.enqueue(new Callback<ResidenteModel>() {
                @Override
                public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            residenteDao.update(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        residenteDao.update(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(ResidenteModel model){
        ResidenteModel modelAnterior = residenteDao.getById(model.getIdResidente());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteDao.delete(model);
            });
            Call<ResidenteModel> call = residenteService.delete(model.getIdResidente());
            call.enqueue(new Callback<ResidenteModel>() {
                @Override
                public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            residenteDao.insert(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        residenteDao.insert(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public ResidenteModel getById(long id){
        try{
            Call<ResidenteModel> call = residenteService.findById(id);
            call.enqueue(new Callback<ResidenteModel>() {
                @Override
                public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                    if(response.isSuccessful()){
                        residenteModel = response.body();
                        if(residenteModel.getIdResidente() == residenteDao.getById(residenteModel.getIdResidente()).getIdResidente()){
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                residenteDao.update(residenteModel);
                            });
                        }else {
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                residenteDao.insert(residenteModel);
                            });
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return residenteModel;
    }

    public List<ResidenteModel> getList(){
        try{
            Call<List<ResidenteModel>> call = residenteService.findAll();
            call.enqueue(new Callback<List<ResidenteModel>>() {
                @Override
                public void onResponse(Call<List<ResidenteModel>> call, Response<List<ResidenteModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        for (int i = 0; i<list.size(); i++){
                            ResidenteModel residente = list.get(i);
                            if(residente.getIdResidente() == residenteDao.getById(residente.getIdResidente()).getIdResidente()){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        MyDataBase.databaseWriteExecutor.execute(() ->{
                                            //residenteDao.update(residente);
                                        });
                                    }
                                }).start();
                            }else {
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    residenteDao.insert(residente);
                                });
                            }
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ResidenteModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }


}
