package br.com.fiap.medibox.business;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.MedicamentoDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.MedicamentoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicamentoBusiness {

    private MedicamentoDao medicamentoDao;

    private Context context;

    private MedicamentoService medicamentoService;

    private MedicamentoModel medicamentoModel;

    private List<MedicamentoModel> list = new ArrayList<MedicamentoModel>();

    MedicamentoBusiness(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        medicamentoDao = db.medicamentoDao();
        medicamentoService = APIUtils.getMedicamentoService();
        context = application.getApplicationContext();
    }

    public void insert(MedicamentoModel model){
        try{
            medicamentoDao.insert(model);
            Call<MedicamentoModel> call = medicamentoService.save(model);
            call.enqueue(new Callback<MedicamentoModel>() {
                @Override
                public void onResponse(Call<MedicamentoModel> call, Response<MedicamentoModel> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        medicamentoDao.delete(model);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MedicamentoModel> call, Throwable t) {
                    medicamentoDao.delete(model);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            medicamentoDao.delete(model);
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(MedicamentoModel model){
        MedicamentoModel modelAnterior = medicamentoDao.getById(model.getIdMedicamento());
        try{
            medicamentoDao.update(model);
            Call<MedicamentoModel> call = medicamentoService.update(model.getIdMedicamento(),model);
            call.enqueue(new Callback<MedicamentoModel>() {
                @Override
                public void onResponse(Call<MedicamentoModel> call, Response<MedicamentoModel> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        medicamentoDao.update(modelAnterior);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MedicamentoModel> call, Throwable t) {
                    medicamentoDao.update(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });


        }catch (Exception e){
            medicamentoDao.update(modelAnterior);
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(MedicamentoModel model){
        MedicamentoModel modelAnterior = medicamentoDao.getById(model.getIdMedicamento());
        try{
            medicamentoDao.delete(model);
            Call<MedicamentoModel> call = medicamentoService.delete(model.getIdMedicamento());
            call.enqueue(new Callback<MedicamentoModel>() {
                @Override
                public void onResponse(Call<MedicamentoModel> call, Response<MedicamentoModel> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Operação relaizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        medicamentoDao.insert(modelAnterior);
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MedicamentoModel> call, Throwable t) {
                    medicamentoDao.insert(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            medicamentoDao.insert(modelAnterior);
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
                        medicamentoDao.insert(medicamentoModel);
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

    public List<MedicamentoModel> getList(){
        try{
            Call<List<MedicamentoModel>> call = medicamentoService.findAll();
            call.enqueue(new Callback<List<MedicamentoModel>>() {
                @Override
                public void onResponse(Call<List<MedicamentoModel>> call, Response<List<MedicamentoModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        medicamentoDao.insertAll(list);
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
