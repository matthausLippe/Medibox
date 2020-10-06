package br.com.fiap.medibox.business;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

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

public class CaixaBusiness {

    private Context context;

    private CaixaDao caixaDao;

    private CaixaService caixaService;

    private List<CaixaModel> listModel = new ArrayList<CaixaModel>();
    private CaixaModel caixaModel;

    CaixaBusiness (Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        caixaDao = db.caixaDao();
        context = application.getApplicationContext();
        caixaService = APIUtils.getCaixaService();
    }

    //Obter lista
    public List<CaixaModel> getList(){
        try{
            Call<List<CaixaModel>> call = caixaService.findAll();
            call.enqueue(new Callback<List<CaixaModel>>() {
                @Override
                public void onResponse(Call<List<CaixaModel>> call, Response<List<CaixaModel>> response) {
                    if(response.isSuccessful()) {
                        listModel = response.body();
                        caixaDao.insertAll(listModel);
                    }else {
                        listModel = (List<CaixaModel>) caixaDao.getAll();
                    }
                }

                @Override
                public void onFailure(Call<List<CaixaModel>> call, Throwable t) {
                    listModel = (List<CaixaModel>) caixaDao.getAll();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show();
        }
        return listModel;
    }

    //Obter 1 item
    public CaixaModel getById(long id){
        try{
            Call<CaixaModel> call = caixaService.findById(id);
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    if (response.isSuccessful()){
                        caixaModel = response.body();
                        caixaDao.insert(caixaModel);
                    }else {
                        caixaModel = caixaDao.getById(id);
                    }
                }

                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    caixaModel = caixaDao.getById(id);
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show();
        }
        return caixaModel;
    }

    //inserir 1 item no Banco de dados
    public void insert(CaixaModel model){
        try {
            caixaDao.insert(model);
            Call<CaixaModel> call = caixaService.save(model);
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    Toast.makeText(context, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao registrar o cadastro!", Toast.LENGTH_SHORT).show();
                    caixaDao.delete(model);
                }
            });
        }catch (Exception e){
            caixaDao.delete(model);
            Toast.makeText(context, "Falha ao registrar o cadastro!", Toast.LENGTH_SHORT).show();
        }
    }


    //Atualizar informação na API e no Banco de Dados
    public void update(CaixaModel model){
        CaixaModel modelAnterior = caixaDao.getById(model.getIdCaixa());
        try {
            caixaDao.update(model);
            Call<CaixaModel> call = caixaService
                    .update(model.getIdCaixa()
                            , model);
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    Toast.makeText(context, "Atualizado com Sucesso!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    caixaDao.update(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            caixaDao.update(modelAnterior);
            Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
        }
    }


    //Deletar item
    public void delete(CaixaModel model){
        CaixaModel modelAnterior = caixaDao.getById(model.getIdCaixa());
        try {
            caixaService.delete(model.getIdCaixa());
            Call<CaixaModel> call = caixaService
                    .delete(model.getIdCaixa());
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    Toast.makeText(context, "Atualizado com Sucesso!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {
                    caixaDao.insert(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            if (caixaDao.getById(modelAnterior.getIdCaixa()).equals(modelAnterior)){
                caixaDao.insert(modelAnterior);
            }

            Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
        }
    }



}
