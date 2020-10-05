package br.com.fiap.medibox.business;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.dao.CaixaDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.CaixaService;
import br.com.fiap.medibox.model.CaixaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaixaBusiness {

    private Context context;

    private CaixaDao caixaDao;

    private CaixaService caixaService;

    private List<CaixaModel> listModel = new ArrayList<CaixaModel>();
    private CaixaModel model;



    CaixaBusiness (Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        caixaDao = db.caixaDao();
        context = application.getApplicationContext();
        caixaService = APIUtils.getCaixaService();
    }


    public List<CaixaModel> getLit(){
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

    public CaixaModel get(long id){
        try{
            Call<CaixaModel> call = caixaService.findById(id);
            call.enqueue(new Callback<CaixaModel>() {
                @Override
                public void onResponse(Call<CaixaModel> call, Response<CaixaModel> response) {
                    if (response.isSuccessful()){
                        model = response.body();
                        caixaDao.insert(model);
                    }else {
                        model = caixaDao.getById(id);
                    }
                }

                @Override
                public void onFailure(Call<CaixaModel> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
        return model;
    }



}
