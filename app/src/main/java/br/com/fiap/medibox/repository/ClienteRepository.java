package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.ClienteDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.ClienteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteRepository {

    private ClienteDao clienteDao;

    private Context context;

    private ClienteService clienteService;
    private ClienteModel clienteModel;
    private ClienteModel currentItem;
    private ClienteModel modelAnterior;
    private MutableLiveData<ClienteModel> modelLiveData = new MutableLiveData<>();
    private List<ClienteModel> lista = new ArrayList<ClienteModel>();
    private MutableLiveData<List<ClienteModel>> list = new MutableLiveData<>();

    public ClienteRepository(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        clienteDao = db.clienteDao();
        clienteService = APIUtils.getClienteService();
        context = application.getApplicationContext();
    }

    public ClienteModel getByIdService(long id) {
        Call<ClienteModel> call = clienteService.findById(id);
        call.enqueue(new Callback<ClienteModel>() {
            @Override
            public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                if (response.isSuccessful()) {
                    clienteModel = response.body();
                }
            }
            @Override
            public void onFailure(Call<ClienteModel> call, Throwable t) {
                Log.e("ClienteService ", "Erro ao buscar clientes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return clienteModel;
    }

    public MutableLiveData<List<ClienteModel>> getListService() {
        Call<List<ClienteModel>> call = clienteService.findAll();
        call.enqueue(new Callback<List<ClienteModel>>() {
            @Override
            public void onResponse(Call<List<ClienteModel>> call, Response<List<ClienteModel>> response) {
                if (response.isSuccessful()) {
                    list.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<ClienteModel>> call, Throwable t) {
                Log.e("ClienteService ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

    public void saveDb(ClienteModel model) {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    clienteModel = clienteDao.getById(model.getId());
                    if (clienteModel == null) {
                        clienteDao.insert(model);
                        Log.e("ClienteRepository", "Cliente: "+model.getId()+" inserido no DB");
                    } else {
                        clienteDao.update(model);
                        Log.e("ClienteRepository", "Cliente: "+model.getId()+" alterado no DB");
                    }
                } catch (Exception e) {
                    Log.e("ClienteRepository", "Falha ao realizar o insert " + e.getMessage());
                }
            }
        });
    }

    public void saveListDb(List<ClienteModel> lista) {
        if (lista != null) {
            for(int i = 0; i<lista.size(); i++){
                ClienteModel model = lista.get(i);
                saveDb(model);
            }
        }
    }

    public MutableLiveData<List<ClienteModel>> getListDb(){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list.postValue(clienteDao.getAll());
            }
        });
        return list;
    }

    public ClienteModel getByIdDb(long id){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                clienteModel = clienteDao.getById(id);
            }
        });
        return clienteModel;
    }



    public void insert(ClienteModel model){
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                clienteDao.insert(model);
            });
            Call<ClienteModel> call = clienteService.save(model);
            call.enqueue(new Callback<ClienteModel>() {
                @Override
                public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            clienteDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ClienteModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        clienteDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (ClienteModel model){
        ClienteModel modelAnterior = clienteDao.getById(model.getId());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                clienteDao.update(model);
            });
            Call<ClienteModel> call = clienteService.update(model.getId(),model);
            call.enqueue(new Callback<ClienteModel>() {
                @Override
                public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            clienteDao.update(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ClienteModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        clienteDao.update(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                clienteDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(ClienteModel model){
        try {
            MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    modelAnterior = clienteDao.getById(model.getId());
                    clienteDao.delete(model);
                    Call<ClienteModel> call = clienteService.delete(model.getId());
                    call.enqueue(new Callback<ClienteModel>() {
                        @Override
                        public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                            } else {
                                MyDataBase.databaseWriteExecutor.execute(() -> {
                                    clienteDao.insert(modelAnterior);
                                });
                                Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ClienteModel> call, Throwable t) {
                            MyDataBase.databaseWriteExecutor.execute(() -> {
                                clienteDao.insert(modelAnterior);
                            });
                            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            MyDataBase.databaseWriteExecutor.execute(() -> {
                clienteDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public MutableLiveData<ClienteModel> getById(long id) {
        Call<ClienteModel> call = clienteService.findById(id);
        call.enqueue(new Callback<ClienteModel>() {
            @Override
            public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                if (response.isSuccessful()) {
                    modelLiveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ClienteModel> call, Throwable t) {
                Log.e("ClienteService   ", "Erro ao buscar cliente:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return modelLiveData;
    }

    public MutableLiveData<List<ClienteModel>> getList(){
        try{
            Call<List<ClienteModel>> call = clienteService.findAll();
            call.enqueue(new Callback<List<ClienteModel>>() {
                @Override
                public void onResponse(Call<List<ClienteModel>> call, Response<List<ClienteModel>> response) {
                    if(response.isSuccessful()){
                        list.postValue(response.body());
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ClienteModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }




}
