package br.com.fiap.medibox.business;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.fiap.medibox.dao.ResidenteMedicamentoDao;
import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.service.APIUtils;
import br.com.fiap.medibox.service.ResidenteMedicamentoService;
import br.com.fiap.medibox.service.TimeLineService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidenteMedicamentoBusiness {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd@HH:mm");

    private Context context;

    private ResidenteMedicamentoDao residenteMedicamentoDao;
    private TimeLineDao timeLineDao;

    private ResidenteMedicamentoService residenteMedicamentoService;
    private TimeLineService timeLineService;

    private List<ResidenteMedicamentoModel> listResidenteMedicamento;
    private ResidenteMedicamentoModel residenteMedicamentoModel;

    private Boolean result = false;

    ResidenteMedicamentoBusiness(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        residenteMedicamentoDao = db.residenteMedicamentoDao();
        timeLineDao = db.timeLineDao();
        residenteMedicamentoService = APIUtils.getResidenteMedicamentoService();
        context = application.getApplicationContext();

    }

    //Obter lista de ResidenteMedicamento
    public List<ResidenteMedicamentoModel> getList(){
        listResidenteMedicamento = new ArrayList<ResidenteMedicamentoModel>();
        try {
            Call<List<ResidenteMedicamentoModel>> call = residenteMedicamentoService.findAll();
            call.enqueue(new Callback<List<ResidenteMedicamentoModel>>() {
                @Override
                public void onResponse(Call<List<ResidenteMedicamentoModel>> call, Response<List<ResidenteMedicamentoModel>> response) {
                    if (response.isSuccessful()) {
                        listResidenteMedicamento = response.body();
                        residenteMedicamentoDao.insertAll(listResidenteMedicamento);
                    } else {
                        listResidenteMedicamento = (List<ResidenteMedicamentoModel>) residenteMedicamentoDao.getAll();
                    }
                }

                @Override
                public void onFailure(Call<List<ResidenteMedicamentoModel>> call, Throwable t) {
                    listResidenteMedicamento = (List<ResidenteMedicamentoModel>) residenteMedicamentoDao.getAll();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show();
        }
        return listResidenteMedicamento;
    }

    // Obter item ResidenteMedicamento
    public ResidenteMedicamentoModel get(long id){
        try {
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.findById(id);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    residenteMedicamentoModel = response.body();
                    residenteMedicamentoDao.insert(residenteMedicamentoModel);
                }

                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(context, "Falha ao carregar dados!", Toast.LENGTH_SHORT).show();
        }
        return residenteMedicamentoModel;
    }

    //Inserir item Residente Medicamento
    public void insert(ResidenteMedicamentoModel model){
        result = false;
        try {
            residenteMedicamentoDao.insert(model);
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService.save(model);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    Toast.makeText(context, "Medicamento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    List<TimeLineModel> listaTimeLine = new ArrayList<TimeLineModel>();
                    for (int i = 0; i<model.getDoses(); i++){
                        listaTimeLine.add(gerarItensTimeLine(model));
                    }
                    timeLineDao.insertAll(listaTimeLine);
                }

                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    Toast.makeText(context, "Falha ao registrar o cadastro!", Toast.LENGTH_SHORT).show();
                    residenteMedicamentoDao.delete(model);
                }
            });
        }catch (Exception e){
            residenteMedicamentoDao.delete(model);
            Toast.makeText(context, "Falha ao registrar o cadastro!", Toast.LENGTH_SHORT).show();
        }
    }

    //Atualizar informação na API e no Banco de Dados
    public void update(ResidenteMedicamentoModel residenteMedicamentoModel1){
        ResidenteMedicamentoModel modelAnterior = residenteMedicamentoDao.getById(residenteMedicamentoModel1.getIdResidenteMedicamento());
        try {
            residenteMedicamentoDao.update(residenteMedicamentoModel1);
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService
                    .update(residenteMedicamentoModel1.getIdResidenteMedicamento()
                            , residenteMedicamentoModel1);
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    Toast.makeText(context, "Atualizado com Sucesso!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    residenteMedicamentoDao.update(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            residenteMedicamentoDao.update(modelAnterior);
            Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
        }
    }

    //Deletar item
    public void delete(ResidenteMedicamentoModel residenteMedicamentoModel1){
        ResidenteMedicamentoModel modelAnterior = residenteMedicamentoDao.getById(residenteMedicamentoModel1.getIdResidenteMedicamento());
        try {
            residenteMedicamentoDao.delete(residenteMedicamentoModel1);
            Call<ResidenteMedicamentoModel> call = residenteMedicamentoService
                    .delete(residenteMedicamentoModel1.getIdResidenteMedicamento());
            call.enqueue(new Callback<ResidenteMedicamentoModel>() {
                @Override
                public void onResponse(Call<ResidenteMedicamentoModel> call, Response<ResidenteMedicamentoModel> response) {
                    Toast.makeText(context, "Atualizado com Sucesso!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ResidenteMedicamentoModel> call, Throwable t) {
                    residenteMedicamentoDao.insert(modelAnterior);
                    Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            if (residenteMedicamentoDao.getById(modelAnterior.getIdResidenteMedicamento()).equals(modelAnterior)){
                residenteMedicamentoDao.insert(modelAnterior);
            }

            Toast.makeText(context, "Falha ao realizar alterações!", Toast.LENGTH_SHORT).show();
        }
    }

    //Gerar items para a TimeLine na hora que inserir os dados
    private TimeLineModel gerarItensTimeLine(ResidenteMedicamentoModel residenteMedicamentoModel){
        Date data = residenteMedicamentoModel.getDataHoraInicio();
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.add(Calendar.HOUR, (int) residenteMedicamentoModel.getIntervalo());
        String novaData = dateFormat.format(gc.getTime());
        data = Date.valueOf(novaData);
        TimeLineModel timeLineModel = new TimeLineModel(residenteMedicamentoModel.getIdResidenteMedicamento(),
                residenteMedicamentoModel.getIdCliente(), data, TimeLineModel.NAO_MEDICADO);
        return timeLineModel;
    }
}
