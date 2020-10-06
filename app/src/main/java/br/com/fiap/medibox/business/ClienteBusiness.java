package br.com.fiap.medibox.business;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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

public class ClienteBusiness {

    private ClienteDao clienteDao;

    private Context context;

    private ClienteService clienteService;
    private ClienteModel clienteModel;
    private List<ClienteModel> list = new ArrayList<ClienteModel>();

    ClienteBusiness(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        clienteDao = db.clienteDao();
        clienteService = APIUtils.getClienteService();
        context = application.getApplicationContext();
    }

    public void insert(ClienteModel model){
        try{
            clienteDao.insert(model);
            Call<ClienteModel> call = clienteService.save(model);
            call.enqueue(new Callback<ClienteModel>() {
                @Override
                public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        clienteDao.delete(model);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ClienteModel> call, Throwable t) {
                    clienteDao.delete(model);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (ClienteModel model){
        ClienteModel modelAnterior = clienteDao.getById(model.getIdCliente());
        try{
            clienteDao.update(model);
            Call<ClienteModel> call = clienteService.update(model.getIdCliente(),model);
            call.enqueue(new Callback<ClienteModel>() {
                @Override
                public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        clienteDao.update(modelAnterior);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ClienteModel> call, Throwable t) {
                    clienteDao.update(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            clienteDao.update(modelAnterior);
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(ClienteModel model){
        ClienteModel modelAnterior = clienteDao.getById(model.getIdCliente());
        try{
            Call<ClienteModel> call = clienteService.delete(model.getIdCliente());
            call.enqueue(new Callback<ClienteModel>() {
                @Override
                public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        clienteDao.insert(modelAnterior);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ClienteModel> call, Throwable t) {
                    clienteDao.insert(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            clienteDao.insert(modelAnterior);
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public ClienteModel getById(long id){
        try{
            Call<ClienteModel> call = clienteService.findById(id);
            call.enqueue(new Callback<ClienteModel>() {
                @Override
                public void onResponse(Call<ClienteModel> call, Response<ClienteModel> response) {
                    if(response.isSuccessful()){
                        clienteModel = response.body();
                        clienteDao.insert(clienteModel);
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ClienteModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return clienteModel;
    }

    public List<ClienteModel> getList(){
        try{
            Call<List<ClienteModel>> call = clienteService.findAll();
            call.enqueue(new Callback<List<ClienteModel>>() {
                @Override
                public void onResponse(Call<List<ClienteModel>> call, Response<List<ClienteModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        clienteDao.insertAll(list);
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
