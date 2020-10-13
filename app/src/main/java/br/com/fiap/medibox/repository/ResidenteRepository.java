package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.ClienteDao;
import br.com.fiap.medibox.dao.ResidenteDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.ResidenteWithCliente;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.ClienteService;
import br.com.fiap.medibox.service.ResidenteService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidenteRepository {

    private ResidenteDao residenteDao;
    private ClienteDao clienteDao;
    private ClienteRepository clienteRepository;
    private ResidenteMedicamentoRepository residenteMedicamentoRepository;

    private Context context;

    private ResidenteService residenteService;
    private ClienteService clienteService;
    private ResidenteModel residenteModel;
    private MutableLiveData<List<ResidenteModel>> listaModel = new MutableLiveData<>();
    private MutableLiveData<List<ResidenteWithCliente>> rwc = new MutableLiveData<>();
    private List<ResidenteModel> lista = new ArrayList<>();
    private List<ClienteModel> listClientes = new ArrayList<ClienteModel>();
    private MyDataBase db;
    private MutableLiveData<ResidenteModel> modelLiveData = new MutableLiveData<>();
    private MutableLiveData<ResidenteWithCliente> residenteWithCliente = new MutableLiveData<>();

    boolean ready = false;
    private LiveData<List<ResidenteModel>> listaLiveData;

    public ResidenteRepository(Application application) {
        db = Room.databaseBuilder(application.getApplicationContext(), MyDataBase.class, "MyDatabase").build();
        residenteService = APIUtils.getResidenteService();
        clienteService = APIUtils.getClienteService();
        context = application.getApplicationContext();
        residenteDao = db.residenteDao();
        clienteDao = db.clienteDao();
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
        clienteRepository = new ClienteRepository(application);
    }

    public void insert(ResidenteModel model) {
        try {
            Call<ResidenteModel> call = residenteService.save(model);
            call.enqueue(new Callback<ResidenteModel>() {
                @Override
                public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                    if (response.isSuccessful()) {
                        saveDb(model);
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        MyDataBase.databaseWriteExecutor.execute(() -> {
                            residenteDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResidenteModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() -> {
                        residenteDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveDb(ResidenteModel model) {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    residenteModel = residenteDao.getById(model.getId());
                    ClienteModel cliente = clienteDao.getById(model.getIdCliente());
                    if (residenteModel == null && cliente != null) {
                        residenteDao.insert(model);
                        Log.e("ResidenteRepository", "Residente: "+model.getNomeResidente()+" inserido no DB");
                    } else {
                        residenteDao.update(model);
                        Log.e("ResidenteRepository", "Residente: "+model.getNomeResidente()+" alterado no DB");
                    }
                } catch (Exception e) {
                    Log.e("ResidenteRepository", "Falha ao realizar o insert " + e.getMessage());
                }
            }
        });
    }

    public boolean update(ResidenteModel model) {
        Call<ResidenteModel> call = residenteService.update(model.getId(), model);
        call.enqueue(new Callback<ResidenteModel>() {
            @Override
            public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                if (response.isSuccessful()) {
                    saveDb(model);
                    ready = true;
                    Toast.makeText(context, "Residente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.e("ResidenteRepository", "Inserido com sucesso ");
                }else{
                    Toast.makeText(context, "Falha ao atualizar os dados.", Toast.LENGTH_SHORT).show();
                    Log.e("ResidenteRepository", "Falha ao atualizar os dados " );
                    ready = false;
                }
            }

            @Override
            public void onFailure(Call<ResidenteModel> call, Throwable t) {
                Toast.makeText(context, "Falha a atualizar o dados.", Toast.LENGTH_SHORT).show();
                Log.e("ResidenteRepository", "Falha na requisição" + t.getMessage());
                ready = false;
            }
        });
        return ready;
    }

    public void saveListDb(List<ResidenteModel> lista) {
        if (lista != null) {
            for(int i = 0; i<lista.size(); i++){
                ResidenteModel model = lista.get(i);
                saveDb(model);
            }
        }
    }

    public MutableLiveData<List<ResidenteModel>> getListService() {
        Call<List<ResidenteModel>> call = residenteService.findAll();
        call.enqueue(new Callback<List<ResidenteModel>>() {
            @Override
            public void onResponse(Call<List<ResidenteModel>> call, Response<List<ResidenteModel>> response) {
                if (response.isSuccessful()) {
                    lista = response.body();
                    listaModel.postValue(lista);
                }
            }
            @Override
            public void onFailure(Call<List<ResidenteModel>> call, Throwable t) {
                Log.e("ResidenteService   ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return listaModel;
    }

    public MutableLiveData<ResidenteModel> getById(long id) {
        Call<ResidenteModel> call = residenteService.findById(id);
        call.enqueue(new Callback<ResidenteModel>() {
            @Override
            public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                if (response.isSuccessful()) {
                    modelLiveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResidenteModel> call, Throwable t) {
                Log.e("ResidenteService   ", "Erro ao buscar residente:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return modelLiveData;
    }

    public ResidenteModel getByIdDb(long id){
           MyDataBase.databaseWriteExecutor.execute(new Runnable() {
               @Override
               public void run() {
                   residenteModel = residenteDao.getById(id);
                   residenteModel.setCliente(clienteDao.getById(residenteModel.getIdCliente()));
               }
           });
           return residenteModel;
    }

    public MutableLiveData<ResidenteWithCliente> getResidenteWithCliente (long id){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                residenteWithCliente.postValue(residenteDao.getResidenteCliente(id));
            }
        });
        return residenteWithCliente;
    }

    public MutableLiveData<ResidenteModel> getByIdService(long id) {
        Call<ResidenteModel> call = residenteService.findById(id);
        call.enqueue(new Callback<ResidenteModel>() {
            @Override
            public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                if (response.isSuccessful()) {
                    modelLiveData.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ResidenteModel> call, Throwable t) {
                Log.e("ResidenteService   ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return modelLiveData;
    }

    public MutableLiveData<List<ResidenteModel>> getListDb() {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
               listaModel.postValue(residenteDao.getAll());
            }
        });
        return listaModel;
    }

    public boolean delete(long id){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                residenteModel = residenteDao.getById(id);
            }
        });
        if(residenteModel != null) {
            Call<ResidenteModel> call = residenteService.delete(id);
            call.enqueue(new Callback<ResidenteModel>() {
                @Override
                public void onResponse(Call<ResidenteModel> call, Response<ResidenteModel> response) {
                    if (response.isSuccessful()) {
                        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        residenteDao.delete(residenteDao.getById(id));
                                        ready = true;
                                    }
                                });
                                Toast.makeText(context,"Excluido com sucesso!", Toast.LENGTH_SHORT).show();
                                Log.e("ResidenteRepository", "Excluido com sucesso!");
                            }
                        });
                    }

                }

                @Override
                public void onFailure(Call<ResidenteModel> call, Throwable t) {

                }
            });
        }
        return ready;
    }
}
