package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.EnderecoDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.EnderecoModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.EnderecoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnderecoRepository {

    private EnderecoDao enderecoDao;

    private Context context;

    private EnderecoService enderecoService;
    private EnderecoModel enderecoModel;
    private List<EnderecoModel> list = new ArrayList<EnderecoModel>();
    private MutableLiveData<List<EnderecoModel>> listaModel = new MutableLiveData<>();

    public EnderecoRepository(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        enderecoDao = db.enderecoDao();
        enderecoService = APIUtils.getEnderecoService();
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<EnderecoModel>> getListService() {
        Call<List<EnderecoModel>> call = enderecoService.findAll();
        call.enqueue(new Callback<List<EnderecoModel>>() {
            @Override
            public void onResponse(Call<List<EnderecoModel>> call, Response<List<EnderecoModel>> response) {
                if (response.isSuccessful()) {
                    listaModel.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<EnderecoModel>> call, Throwable t) {
                Log.e("EnderecoRepository ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return listaModel;
    }

    public void saveDb(EnderecoModel model) {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    enderecoModel = enderecoDao.getById(model.getIdEndereco());
                    if (enderecoModel == null) {
                        enderecoDao.insert(model);
                        Log.e("EnderecoRepository", "Endereco: "+model.getIdEndereco()+" inserido no DB");
                    } else {
                        enderecoDao.update(model);
                        Log.e("EnderecoRepository", "Endereco: "+model.getIdEndereco()+" alterado no DB");
                    }
                } catch (Exception e) {
                    Log.e("EnderecoRepository", "Falha ao realizar o insert " + e.getMessage());
                }
            }
        });
    }

    public void saveListDb(List<EnderecoModel> lista) {
        if (lista != null) {
            for(int i = 0; i<lista.size(); i++){
                EnderecoModel model = lista.get(i);
                saveDb(model);
            }
        }
    }

    public void insert(EnderecoModel model){
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                enderecoDao.insert(model);
            });
            Call<EnderecoModel> call = enderecoService.save(model);
            call.enqueue(new Callback<EnderecoModel>() {
                @Override
                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            enderecoDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EnderecoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        enderecoDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (EnderecoModel model){
        EnderecoModel modelAnterior = enderecoDao.getById(model.getIdEndereco());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                enderecoDao.update(model);
            });
            Call<EnderecoModel> call = enderecoService.update(model.getIdEndereco(),model);
            call.enqueue(new Callback<EnderecoModel>() {
                @Override
                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            enderecoDao.update(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EnderecoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        enderecoDao.update(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                enderecoDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(EnderecoModel model){
        EnderecoModel modelAnterior = enderecoDao.getById(model.getIdEndereco());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                enderecoDao.delete(model);
            });
            Call<EnderecoModel> call = enderecoService.delete(model.getIdEndereco());
            call.enqueue(new Callback<EnderecoModel>() {
                @Override
                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            enderecoDao.insert(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EnderecoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        enderecoDao.insert(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                enderecoDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public EnderecoModel getById(long id){
        try{
            Call<EnderecoModel> call = enderecoService.findById(id);
            call.enqueue(new Callback<EnderecoModel>() {
                @Override
                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response) {
                    if(response.isSuccessful()){
                        enderecoModel = response.body();
                        if(enderecoModel.getIdEndereco() == enderecoDao.getById(enderecoModel.getIdEndereco()).getIdEndereco()){
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                enderecoDao.update(enderecoModel);
                            });
                        }else {
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                enderecoDao.insert(enderecoModel);
                            });
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EnderecoModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return enderecoModel;
    }

    public List<EnderecoModel> getList(){
        try{
            Call<List<EnderecoModel>> call = enderecoService.findAll();
            call.enqueue(new Callback<List<EnderecoModel>>() {
                @Override
                public void onResponse(Call<List<EnderecoModel>> call, Response<List<EnderecoModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        for (int i = 0; i<list.size(); i++){
                            EnderecoModel endereco = list.get(i);
                            if(endereco.getIdEndereco() == enderecoDao.getById(endereco.getIdEndereco()).getIdEndereco()){
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    enderecoDao.update(endereco);
                                });
                            }else {
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    enderecoDao.insert(endereco);
                                });
                            }
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<EnderecoModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }


 }
