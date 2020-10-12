package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.fiap.medibox.dao.ResidenteMedicamentoDao;
import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.ResidenteMedicamentoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidenteMedicamentoRepository {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd@HH:mm");

    private ResidenteMedicamentoDao residenteMedicamentoDao;
    private TimeLineDao timeLineDao;
    private ResidenteMedicamentoService residenteMedicamentoService;

    private Context context;
    
    private ResidenteMedicamentoModel residenteMedicamentoModel;
    MutableLiveData<List<ResidenteMedicamentoModel>> list = new MutableLiveData<>();
    private MyDataBase db;

    boolean ready = false;


    public ResidenteMedicamentoRepository(Application application){
        db = MyDataBase.getDatabase(application);
        residenteMedicamentoDao = db.residenteMedicamentoDao();
        timeLineDao = db.timeLineDao();
        residenteMedicamentoService = APIUtils.getResidenteMedicamentoService();
        context = application.getApplicationContext();

    }

    public void insert(ResidenteMedicamentoModel model) {
        try {
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.save(model);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    if (response.isSuccessful()) {
                        saveDb(model);
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        MyDataBase.databaseWriteExecutor.execute(() -> {
                            residenteMedicamentoDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() -> {
                        residenteMedicamentoDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveDb(ResidenteMedicamentoModel model) {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    residenteMedicamentoModel = residenteMedicamentoDao.getById(model.getIdResidente());
                    if (residenteMedicamentoModel == null) {
                        residenteMedicamentoDao.insert(model);
                        Log.e("ResidenteMedicamentoRepository", "Residente Medicamento: "+model.getIdResidenteMedicamento()+" inserido no DB");
                    } else {
                        residenteMedicamentoDao.update(model);
                        Log.e("ResidenteMedicamentoRepository", "Residente: "+model.getIdResidenteMedicamento()+" alterado no DB");
                    }
                } catch (Exception e) {
                    Log.e("ResidenteMedicamentoRepository", "Falha ao realizar o insert " + e.getMessage());
                }
            }
        });
    }

    public boolean update(ResidenteMedicamentoModel model) {
        Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.update(model.getIdResidente(), model);
        call.enqueue(new Callback<ResidenteMedicamentoModel>() {
            @Override
            public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                if (response.isSuccessful()) {
                    saveDb(model);
                    ready = true;
                    Toast.makeText(context, "Residente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.e("ResidenteMedicamentoRepository", "Inserido com sucesso ");
                }else{
                    Toast.makeText(context, "Falha ao atualizar os dados.", Toast.LENGTH_SHORT).show();
                    Log.e("ResidenteMedicamentoRepository", "Falha ao atualizar os dados " );
                    ready = false;
                }
            }

            @Override
            public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                Toast.makeText(context, "Falha a atualizar o dados.", Toast.LENGTH_SHORT).show();
                Log.e("ResidenteMedicamentoRepository", "Falha na requisição" + t.getMessage());
                ready = false;
            }
        });
        return ready;
    }

    public void saveListDb(List<ResidenteMedicamentoModel> lista) {
        if (lista != null) {
            for(int i = 0; i<lista.size(); i++){
                ResidenteMedicamentoModel model = lista.get(i);
                MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            residenteMedicamentoModel = residenteMedicamentoDao.getById(model.getIdResidente());
                            if (residenteMedicamentoModel == null) {
                                residenteMedicamentoDao.insert(model);
                                Log.e("ResidenteMedicamentoRepository", "Residente Medicamento: "+model.getIdResidenteMedicamento()+" inserido no DB");
                            } else {
                                residenteMedicamentoDao.update(model);
                                Log.e("ResidenteMedicamentoRepository", "Residente Medicamento: "+model.getIdResidenteMedicamento()+" alterado no DB");
                            }
                        } catch (Exception e) {
                            Log.e("ResidenteMedicamentoRepository", "Falha ao realizar o insert " + e.getMessage());
                        }
                    }
                });
            }
        }
    }

    public MutableLiveData<List<ResidenteMedicamentoModel>> getListService() {
        Call<List<ResidenteMedicamentoModel>> call = residenteMedicamentoService.findAll();
        call.enqueue(new Callback<List<ResidenteMedicamentoModel>>() {
            @Override
            public void onResponse(Call<List<ResidenteMedicamentoModel>> call, Response<List<ResidenteMedicamentoModel>> response) {
                if (response.isSuccessful()) {
                    list.postValue(response.body());
                }
                else{
                    Log.e("ResidenteService   ", "Erro ao buscar residentes:" );
                }
            }
            @Override
            public void onFailure(Call<List<ResidenteMedicamentoModel>> call, Throwable t) {
                Log.e("ResidenteService   ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

    public MutableLiveData<List<ResidenteMedicamentoModel>> getByIdResidenteDb(long id){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                list.postValue(residenteMedicamentoDao.getByIdResidente(id));
            }
        });
        return list;
    }

    public boolean delete(long id){
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                residenteMedicamentoModel = residenteMedicamentoDao.getById(id);
            }
        });
        if(residenteMedicamentoModel != null) {
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.delete(id);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    if (response.isSuccessful()) {
                        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                MyDataBase.databaseWriteExecutor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        residenteMedicamentoDao.delete(residenteMedicamentoDao.getById(id));
                                        ready = true;
                                    }
                                });
                                Toast.makeText(context,"Excluido com sucesso!", Toast.LENGTH_SHORT).show();
                                Log.e("ResidenteMedicamentoRepository", "Excluido com sucesso!");
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {

                }
            });
        }

        return ready;
    }


}
