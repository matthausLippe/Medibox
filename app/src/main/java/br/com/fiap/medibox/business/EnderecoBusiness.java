package br.com.fiap.medibox.business;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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

public class EnderecoBusiness {

    private EnderecoDao enderecoDao;

    private Context context;

    private EnderecoService enderecoService;
    private EnderecoModel enderecoModel;
    private List<EnderecoModel> list = new ArrayList<EnderecoModel>();

    EnderecoBusiness(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        enderecoDao = db.enderecoDao();
        enderecoService = APIUtils.getEnderecoService();
        context = application.getApplicationContext();
    }

    public void insert(EnderecoModel model){
        try{
            enderecoDao.insert(model);
            Call<EnderecoModel> call = enderecoService.save(model);
            call.enqueue(new Callback<EnderecoModel>() {
                @Override
                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        enderecoDao.delete(model);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EnderecoModel> call, Throwable t) {
                    enderecoDao.delete(model);
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
            enderecoDao.update(model);
            Call<EnderecoModel> call = enderecoService.update(model.getIdEndereco(),model);
            call.enqueue(new Callback<EnderecoModel>() {
                @Override
                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        enderecoDao.update(modelAnterior);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EnderecoModel> call, Throwable t) {
                    enderecoDao.update(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            enderecoDao.update(modelAnterior);
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(EnderecoModel model){
        EnderecoModel modelAnterior = enderecoDao.getById(model.getIdEndereco());
        try{
            Call<EnderecoModel> call = enderecoService.delete(model.getIdEndereco());
            call.enqueue(new Callback<EnderecoModel>() {
                @Override
                public void onResponse(Call<EnderecoModel> call, Response<EnderecoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        enderecoDao.insert(modelAnterior);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EnderecoModel> call, Throwable t) {
                    enderecoDao.insert(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            enderecoDao.insert(modelAnterior);
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
                        enderecoDao.insert(enderecoModel);
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
                        enderecoDao.insertAll(list);
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
