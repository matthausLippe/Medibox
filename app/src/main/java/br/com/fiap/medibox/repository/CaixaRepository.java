package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.CaixaDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.CaixaModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.CaixaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaixaRepository {

    private Context context;

    private CaixaDao caixaDao;

    private CaixaService caixaService;

    private List<CaixaModel> list = new ArrayList<CaixaModel>();
    private CaixaModel caixaModel;
    private MutableLiveData<List<CaixaModel>> listaModel = new MutableLiveData<>();

    public CaixaRepository(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        caixaDao = db.caixaDao();
        context = application.getApplicationContext();
        caixaService = APIUtils.getCaixaService();
    }

    public MutableLiveData<List<CaixaModel>> getListService() {
        Call<List<CaixaModel>> call = caixaService.findAll();
        call.enqueue(new Callback<List<CaixaModel>>() {
            @Override
            public void onResponse(Call<List<CaixaModel>> call, Response<List<CaixaModel>> response) {
                if (response.isSuccessful()) {
                    listaModel.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<CaixaModel>> call, Throwable t) {
                Log.e("CaixaRepository ", "Erro ao buscar residentes:" + t.getMessage());
                Toast.makeText(context,"Falha ao conectar ao servidor!",Toast.LENGTH_SHORT).show();
            }
        });
        return listaModel;
    }

    public void saveDb(CaixaModel model) {
        MyDataBase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    caixaModel = caixaDao.getById(model.getIdCaixa());
                    if (caixaModel == null) {
                        caixaDao.insert(model);
                        Log.e("CaixaRepository", "Caixa: "+model.getIdCaixa()+" inserido no DB");
                    } else {
                        caixaDao.update(model);
                        Log.e("CaixaRepository", "Caixa: "+model.getIdCaixa()+" alterado no DB");
                    }
                } catch (Exception e) {
                    Log.e("CaixaRepository", "Falha ao realizar o insert " + e.getMessage());
                }
            }
        });
    }

    public void saveListDb(List<CaixaModel> lista) {
        if (lista != null) {
            for(int i = 0; i<lista.size(); i++){
                CaixaModel model = lista.get(i);
                saveDb(model);
            }
        }
    }

    public void insert(CaixaModel model){
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                caixaDao.insert(model);
            });
            Call<CaixaModel> call = caixaService.save(model);
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            caixaDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        caixaDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (CaixaModel model){
        CaixaModel modelAnterior = caixaDao.getById(model.getIdCaixa());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                caixaDao.update(model);
            });
            Call<CaixaModel> call = caixaService.update(model.getIdCaixa(),model);
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            caixaDao.update(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        caixaDao.update(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                caixaDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(CaixaModel model){
        CaixaModel modelAnterior = caixaDao.getById(model.getIdCaixa());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                caixaDao.delete(model);
            });
            Call<CaixaModel> call = caixaService.delete(model.getIdCaixa());
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            caixaDao.insert(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        caixaDao.insert(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                caixaDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public CaixaModel getById(long id){
        try{
            Call<CaixaModel> call = caixaService.findById(id);
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    if(response.isSuccessful()){
                        caixaModel = response.body();
                        if(caixaModel.getIdCaixa() == caixaDao.getById(caixaModel.getIdCaixa()).getIdCaixa()){
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                caixaDao.update(caixaModel);
                            });
                        }else {
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                caixaDao.insert(caixaModel);
                            });
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return caixaModel;
    }

    public List<CaixaModel> getList(){
        try{
            Call<List<CaixaModel>> call = caixaService.findAll();
            call.enqueue(new Callback<List<CaixaModel>>() {
                @Override
                public void onResponse(Call<List<CaixaModel>> call, Response<List<CaixaModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        for (int i = 0; i<list.size(); i++){
                            CaixaModel caixa = list.get(i);
                            if(caixa.getIdCaixa() == caixaDao.getById(caixa.getIdCaixa()).getIdCaixa()){
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    caixaDao.update(caixa);
                                });
                            }else {
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    caixaDao.insert(caixa);
                                });
                            }
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<CaixaModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }


}
