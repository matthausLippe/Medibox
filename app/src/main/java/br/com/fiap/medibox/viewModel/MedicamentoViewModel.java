package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.repository.GavetaRepository;
import br.com.fiap.medibox.repository.MedicamentoRepository;

public class MedicamentoViewModel extends AndroidViewModel {

    private MedicamentoRepository medicamentoRepository;
    private GavetaRepository gavetaRepository;
    private MutableLiveData<List<MedicamentoModel>> list;
    private MedicamentoModel medicamentoModel;

    public MedicamentoViewModel(@NonNull Application application) {
        super(application);
        medicamentoRepository = new MedicamentoRepository(application);
        gavetaRepository = new GavetaRepository(application);
    }

    public LiveData<List<MedicamentoModel>> getListMedicamento(){
        list = medicamentoRepository.getList();
        return list;
    }

    public void saveList(List<MedicamentoModel> lista){
        medicamentoRepository.saveListDb(lista);
    }

    public MutableLiveData<Long> update(MedicamentoModel model){
        return medicamentoRepository.update(model);
    }

    public MutableLiveData<List<GavetaModel>> getListGaveta(){
        return gavetaRepository.getListService();
    }

    public MutableLiveData<MedicamentoModel> getById(long id){
        return medicamentoRepository.getById(id);
    }

    public MutableLiveData<GavetaModel> getGavetaById(long id){
        return gavetaRepository.getById(id);
    }

    public MutableLiveData<Long> save(MedicamentoModel model){
        return medicamentoRepository.update(model);
    }

    public void delete(MedicamentoModel medicamentoModel){
        medicamentoRepository.delete(medicamentoModel);
    }

}
