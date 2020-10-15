package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.repository.MedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteRepository;
import br.com.fiap.medibox.repository.TimeLineRepository;

public class ResidenteMedicamentoViewModel extends AndroidViewModel {

    private ResidenteMedicamentoRepository residenteMedicamentoRepository;
    private MedicamentoRepository medicamentoRepository;
    private ResidenteRepository residenteRepository;
    private TimeLineRepository timeLineRepository;
    private MutableLiveData<List<ResidenteMedicamentoModel>> list;
    private ResidenteMedicamentoModel residenteMedicamentoModel;

    public ResidenteMedicamentoViewModel(@NonNull Application application) {
        super(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
        medicamentoRepository = new MedicamentoRepository(application);
        residenteRepository = new ResidenteRepository(application);
        timeLineRepository = new TimeLineRepository(application);
    }

    public LiveData<List<ResidenteMedicamentoModel>> getListResidente() {
        list = residenteMedicamentoRepository.getListService();
        return list;
    }

    public void saveList(List<ResidenteMedicamentoModel> lista) {
        residenteMedicamentoRepository.saveListDb(lista);
    }

    public MutableLiveData<Long> update(ResidenteMedicamentoModel model) {
        return residenteMedicamentoRepository.update(model);
    }

    public boolean delete(long id) {
        return residenteMedicamentoRepository.delete(id);
    }

    public MutableLiveData<List<MedicamentoModel>> getListMedicamento(){
        return medicamentoRepository.getList();
    }

    public MutableLiveData<ResidenteMedicamentoModel> getById(long id){
        return residenteMedicamentoRepository.getById(id);
    }

    public MutableLiveData<ResidenteModel> getResidenteById(long id) {
        return residenteRepository.getById(id);
    }

    public MutableLiveData<MedicamentoModel> getMedicamentoById(long id){
        return medicamentoRepository.getById(id);
    }

    public MutableLiveData<Long> save(ResidenteMedicamentoModel model) {
        return residenteMedicamentoRepository.update(model);
    }

    public void insertListTimeLine(long idResidenteMedicamento, List<TimeLineModel> list){
        timeLineRepository.gerarLoteTimeLine(idResidenteMedicamento, list);
    }
}
