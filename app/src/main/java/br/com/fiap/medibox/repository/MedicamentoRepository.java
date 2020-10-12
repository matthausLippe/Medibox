package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fiap.medibox.dao.MedicamentoDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.MedicamentoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoRepository {

    private MedicamentoDao medicamentoDao;

    private Context context;

    private MedicamentoService medicamentoService;

    private MedicamentoModel medicamentoModel;

    private MutableLiveData<List<MedicamentoModel>> list = new MutableLiveData<>();

    private boolean ready = false;

    public MedicamentoRepository(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        medicamentoDao = db.medicamentoDao();
        medicamentoService = APIUtils.getMedicamentoService();
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<MedicamentoModel>> getListService() {
        Call<List<MedicamentoModel>> call = medicamentoService.findAll();
        call.enqueue(new Callback<List<MedicamentoModel>>() {
            @Override
            public void onResponse(Call<List<MedicamentoModel>> call, Response<List<MedicamentoModel>> response) {
                if (response.isSuccessful()) {
                    list.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<MedicamentoModel>> call, Throwable t) {
                Log.e("MedicamentoService   ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

    public void saveDb(MedicamentoModel model) {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    medicamentoModel = medicamentoDao.getById(model.getIdMedicamento());
                    if (medicamentoModel == null) {
                        medicamentoDao.insert(model);
                        Log.e("MedicamentoRepository", "Medicamento: "+model.getNomeMedicamento()+" inserido no DB");
                    } else {
                        medicamentoDao.update(model);
                        Log.e("MedicamentoRepository", "Medicamento: "+model.getNomeMedicamento()+" alterado no DB");
                    }
                } catch (Exception e) {
                    Log.e("MedicamentoRepository", "Falha ao realizar o insert " + e.getMessage());
                }
            }
        });
    }

    public void saveListDb(List<MedicamentoModel> lista) {
        if (lista != null) {
            for(int i = 0; i<lista.size(); i++){
                MedicamentoModel model = lista.get(i);
                saveDb(model);
            }
        }
    }

    public void insert(MedicamentoModel model){
        try{
            Call<MedicamentoModel> call = medicamentoService.save(model);
            call.enqueue(new Callback<MedicamentoModel>() {
                @Override
                public void onResponse(Call<MedicamentoModel> call, Response<MedicamentoModel> response) {
                    if (response.isSuccessful()) {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            medicamentoDao.insert(model);
                        });
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            medicamentoDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MedicamentoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        medicamentoDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean update(MedicamentoModel model) {
        Call<MedicamentoModel> call = medicamentoService.update(model.getIdMedicamento(), model);
        call.enqueue(new Callback<MedicamentoModel>() {
            @Override
            public void onResponse(Call<MedicamentoModel> call, Response<MedicamentoModel> response) {
                if (response.isSuccessful()) {
                    saveDb(model);
                    ready = true;
                    Toast.makeText(context, "Medicamento atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.e("MedicamentoRepository", "Inserido com sucesso ");
                }else{
                    Toast.makeText(context, "Falha ao atualizar os dados.", Toast.LENGTH_SHORT).show();
                    Log.e("MedicamentoRepository", "Falha ao atualizar os dados " );
                    ready = false;
                }
            }

            @Override
            public void onFailure(Call<MedicamentoModel> call, Throwable t) {
                Toast.makeText(context, "Falha a atualizar o dados.", Toast.LENGTH_SHORT).show();
                Log.e("ResidenteRepository", "Falha na requisição" + t.getMessage());
                ready = false;
            }
        });
        return ready;
    }

    public void delete(MedicamentoModel model){
        MedicamentoModel modelAnterior = medicamentoDao.getById(model.getIdMedicamento());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                medicamentoDao.delete(model);
            });
            Call<MedicamentoModel> call = medicamentoService.delete(model.getIdMedicamento());
            call.enqueue(new Callback<MedicamentoModel>() {
                @Override
                public void onResponse(Call<MedicamentoModel> call, Response<MedicamentoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            medicamentoDao.insert(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MedicamentoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        medicamentoDao.insert(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                medicamentoDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public MedicamentoModel getById(long id){
        try{
            Call<MedicamentoModel> call = medicamentoService.findById(id);
            call.enqueue(new Callback<MedicamentoModel>() {
                @Override
                public void onResponse(Call<MedicamentoModel> call, Response<MedicamentoModel> response) {
                    if(response.isSuccessful()){
                        medicamentoModel = response.body();
                        if(medicamentoModel.getIdMedicamento() == medicamentoDao.getById(medicamentoModel.getIdMedicamento()).getIdMedicamento()){
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                medicamentoDao.update(medicamentoModel);
                            });
                        }else {
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                medicamentoDao.insert(medicamentoModel);
                            });
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MedicamentoModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return medicamentoModel;
    }

    public MutableLiveData<List<MedicamentoModel>> getList(){
        try{
            Call<List<MedicamentoModel>> call = medicamentoService.findAll();
            call.enqueue(new Callback<List<MedicamentoModel>>() {
                @Override
                public void onResponse(Call<List<MedicamentoModel>> call, Response<List<MedicamentoModel>> response) {
                    if(response.isSuccessful()){
                        list.postValue(response.body());
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<MedicamentoModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}
