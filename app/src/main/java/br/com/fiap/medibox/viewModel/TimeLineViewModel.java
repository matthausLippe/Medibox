package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.sql.Date;
import java.util.List;

import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.repository.GavetaRepository;
import br.com.fiap.medibox.repository.MedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteRepository;
import br.com.fiap.medibox.repository.TimeLineRepository;

public class TimeLineViewModel extends AndroidViewModel {

    private TimeLineRepository timeLineRepository;
    private ResidenteRepository residenteRepository;
    private MedicamentoRepository medicamentoRepository;
    private ResidenteMedicamentoRepository residenteMedicamentoRepository;
    private GavetaRepository gavetaRepository;
    private MutableLiveData<List<TimeLineModel>> list;
    private List<TimeLineModel> lista;

    public TimeLineViewModel(@NonNull Application application) {
        super(application);
        timeLineRepository = new TimeLineRepository(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
        medicamentoRepository = new MedicamentoRepository(application);
        residenteRepository = new ResidenteRepository(application);
        gavetaRepository = new GavetaRepository(application);
    }

    public MutableLiveData<List<TimeLineModel>> getLitNotification(Date date){
        list = timeLineRepository.getListNotification(date);
        return list;
    }

    public MutableLiveData<List<TimeLineModel>> getList(){
        return timeLineRepository.getListService();
    }

    public MutableLiveData<ResidenteModel> getResidenteById(long id){
        return residenteRepository.getById(id);
    }

    public MutableLiveData<MedicamentoModel> getMedicamentoById(long id){
        return medicamentoRepository.getById(id);
    }

    public MutableLiveData<ResidenteMedicamentoModel> getResidenteMedicamentoById(long id){
        return residenteMedicamentoRepository.getById(id);
    }
    public MutableLiveData<List<ResidenteModel>> getListResidente(){
        return residenteRepository.getListService();
    }

    public MutableLiveData<List<MedicamentoModel>> getListMedicamento(){
        return medicamentoRepository.getListService();
    }

    public MutableLiveData<List<ResidenteMedicamentoModel>> getListResidenteMedicamento(){
        return residenteMedicamentoRepository.getListService();
    }

    public MutableLiveData<List<GavetaModel>> getListGaveta(){
        return gavetaRepository.getListService();

    }


}
