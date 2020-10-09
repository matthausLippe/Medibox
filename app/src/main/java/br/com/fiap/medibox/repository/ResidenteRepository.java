package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

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
    private List<ResidenteModel> list;
    List<ResidenteModel> listaModel = new ArrayList<ResidenteModel>();
    private MyDataBase db;

    public ResidenteRepository(Application application){
        db = Room.databaseBuilder(application.getApplicationContext(),MyDataBase.class,"MyDatabase").build();
        residenteService = APIUtils.getResidenteService();
        context = application.getApplicationContext();
        residenteDao = db.residenteDao();
    }

    public void insert(ResidenteModel model){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.residenteDao().insert(model);
            }
        });
        try{
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

    public void insertDb(ResidenteModel model){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
           @Override
           public void run() {
              try{
                  residenteModel = residenteDao.getById(model.getIdResidente());
                  if(model == null) {
                      residenteDao.insert(model);
                  } else{
                      residenteDao.delete(model);
                      residenteDao.insert(model);
                  }
              }catch (Exception e){
                  Log.e("ResidenteRepository","Falha ao realizar o insert "+ e.getMessage());
              }
           }
       });
    }

    public void insertDbAll(List<ResidenteModel> listaModel){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                residenteDao.insertAll(listaModel);
            }
        });
    }

    public ResidenteModel getByIdDb(ResidenteModel model) {
        if(model != null)
            {MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    residenteModel = residenteDao.getById(model.getIdResidente());
                }
            });
        }else {
            Log.e("ResidenteRepository","Model Null getByIdDb");
        }
        return residenteModel;
    }

    public void deleteDb(ResidenteModel model){
        if (model != null){
            MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    residenteDao.delete(model);
                }
            });
        }
    }


    public List<ResidenteModel> getListService() {
        Call<List<ResidenteModel>> call = residenteService.findAll();
        call.enqueue(new Callback<List<ResidenteModel>>() {
            @Override
            public void onResponse(Call<List<ResidenteModel>> call, Response<List<ResidenteModel>> response) {
                list = response.body();
                for(int i = 0; i<list.size(); i++) {
                    ResidenteModel modelDb = getByIdDb(list.get(i));
                    if(modelDb == null || list.get(i).getIdResidente() != modelDb.getIdResidente()) {
                        insertDb(list.get(i));
                        Log.e("ResidenteRepository", "Residente: "
                                + list.get(i).getNomeResidente()
                                + " adicionado no DB");
                    }else {
                        deleteDb(list.get(i));
                        Log.e("Activity","Erro ao comparar model");
                    }
                }
                //populate(list);
            }

            @Override
            public void onFailure(Call<List<ResidenteModel>> call, Throwable t) {
                Log.e("ResidenteService   ", "Erro ao buscar o cep:" + t.getMessage());
            }
        });
        return list;
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

   /* public LiveData<List<ResidenteModel>> getList(){
            Call<List<ResidenteModel>> call = residenteService.findAll();
            call.enqueue(new Callback<List<ResidenteModel>>() {
                @Override
                public void onResponse(Call<List<ResidenteModel>> call, Response<List<ResidenteModel>> response) {
                    if(response.isSuccessful()){
                        List<ResidenteModel> lista1 = response.body();
                        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i<lista1.size(); i++) {
                                    ResidenteModel residente = lista1.get(i);
                                    if (residente.getIdResidente() == residenteDao.getById(residente.getIdResidente()).getIdResidente()) {

                                        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                residenteDao.insert(residente);
                                            }
                                        });
                                    } else {
                                        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                residenteDao.insert(residente);
                                            }
                                        });
                                    }
                                }
                            }
                        });

                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ResidenteModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
            MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    list = residenteDao.getAll();
                }
            });
        return list;
    }*/

}
