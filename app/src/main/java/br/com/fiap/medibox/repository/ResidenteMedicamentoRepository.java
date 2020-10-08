package br.com.fiap.medibox.repository;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.ResidenteMedicamentoDao;
import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.ResidenteMedicamentoService;
import br.com.fiap.medibox.service.TimeLineService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidenteMedicamentoRepository {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd@HH:mm");

    private Context context;

    private ResidenteMedicamentoDao residenteMedicamentoDao;
    private TimeLineDao timeLineDao;

    private ResidenteMedicamentoService residenteMedicamentoService;
    private TimeLineService timeLineService;

    private List<ResidenteMedicamentoModel> list = new ArrayList<ResidenteMedicamentoModel>();
    private ResidenteMedicamentoModel residenteMedicamentoModel;


    public ResidenteMedicamentoRepository(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        residenteMedicamentoDao = db.residenteMedicamentoDao();
        timeLineDao = db.timeLineDao();
        residenteMedicamentoService = APIUtils.getResidenteMedicamentoService();
        context = application.getApplicationContext();

    }

    public void insert(ResidenteMedicamentoModel model){
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteMedicamentoDao.insert(model);
            });
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.save(model);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cadastrado realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else {
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            residenteMedicamentoDao.delete(model);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        residenteMedicamentoDao.delete(model);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void update (ResidenteMedicamentoModel model){
        ResidenteMedicamentoModel modelAnterior = residenteMedicamentoDao.getById(model.getIdResidenteMedicamento());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteMedicamentoDao.update(model);
            });
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.update(model.getIdResidenteMedicamento(),model);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            residenteMedicamentoDao.update(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        residenteMedicamentoDao.update(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteMedicamentoDao.update(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(ResidenteMedicamentoModel model){
        ResidenteMedicamentoModel modelAnterior = residenteMedicamentoDao.getById(model.getIdResidenteMedicamento());
        try{
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteMedicamentoDao.delete(model);
            });
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.delete(model.getIdResidenteMedicamento());
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, "Alteração realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        MyDataBase.databaseWriteExecutor.execute(() ->{
                            residenteMedicamentoDao.insert(modelAnterior);
                        });
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    MyDataBase.databaseWriteExecutor.execute(() ->{
                        residenteMedicamentoDao.insert(modelAnterior);
                    });
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            MyDataBase.databaseWriteExecutor.execute(() ->{
                residenteMedicamentoDao.insert(modelAnterior);
            });
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
    }

    public ResidenteMedicamentoModel getById(long id){
        try{
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.findById(id);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    if(response.isSuccessful()){
                        residenteMedicamentoModel = response.body();
                        if(residenteMedicamentoModel.getIdResidenteMedicamento() == residenteMedicamentoDao.getById(residenteMedicamentoModel.getIdResidenteMedicamento()).getIdResidenteMedicamento()){
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                residenteMedicamentoDao.update(residenteMedicamentoModel);
                            });
                        }else {
                            MyDataBase.databaseWriteExecutor.execute(() ->{
                                residenteMedicamentoDao.insert(residenteMedicamentoModel);
                            });
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return residenteMedicamentoModel;
    }

    public List<ResidenteMedicamentoModel> getList(){
        try{
            Call<List<ResidenteMedicamentoModel>> call = residenteMedicamentoService.findAll();
            call.enqueue(new Callback<List<ResidenteMedicamentoModel>>() {
                @Override
                public void onResponse(Call<List<ResidenteMedicamentoModel>> call, Response<List<ResidenteMedicamentoModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        for (int i = 0; i<list.size(); i++){
                            ResidenteMedicamentoModel residenteMedicamento = list.get(i);
                            if(residenteMedicamento.getIdResidenteMedicamento() == residenteMedicamentoDao.getById(residenteMedicamento.getIdResidenteMedicamento()).getIdResidenteMedicamento()){
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    residenteMedicamentoDao.update(residenteMedicamento);
                                });
                            }else {
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    residenteMedicamentoDao.insert(residenteMedicamento);
                                });
                            }
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ResidenteMedicamentoModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    public List<ResidenteMedicamentoModel> getListByIdResidente(long id) {
        try{
            Call<List<ResidenteMedicamentoModel>> call = residenteMedicamentoService.findByIdResidente(id);
            call.enqueue(new Callback<List<ResidenteMedicamentoModel>>() {
                @Override
                public void onResponse(Call<List<ResidenteMedicamentoModel>> call, Response<List<ResidenteMedicamentoModel>> response) {
                    if(response.isSuccessful()){
                        list = response.body();
                        for (int i = 0; i<list.size(); i++){
                            ResidenteMedicamentoModel residenteMedicamento = list.get(i);
                            if(residenteMedicamento.getIdResidenteMedicamento() == residenteMedicamentoDao.getById(residenteMedicamento.getIdResidenteMedicamento()).getIdResidenteMedicamento()){
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    residenteMedicamentoDao.update(residenteMedicamento);
                                });
                            }else {
                                MyDataBase.databaseWriteExecutor.execute(() ->{
                                    residenteMedicamentoDao.insert(residenteMedicamento);
                                });
                            }
                        }
                    }else{
                        Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<ResidenteMedicamentoModel>> call, Throwable t) {
                    Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao realizar operação!", Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}
