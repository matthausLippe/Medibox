package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteRepository;

public class ResidenteViewModel extends AndroidViewModel {

    private ResidenteRepository residenteRepository;
    private ResidenteMedicamentoRepository residenteMedicamentoRepository;

    private ResidenteModel residenteModel;

    private MutableLiveData<List<ResidenteModel>> list;


    public ResidenteViewModel(@NonNull Application application) {
        super(application);
        residenteRepository = new ResidenteRepository(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
    }


    public LiveData<List<ResidenteModel>> getListResidente() {
        list = residenteRepository.getListService();
        return list;
    }

    public void saveList(List<ResidenteModel> lista){
        residenteRepository.saveListDb(lista);
    }

    public boolean update(ResidenteModel model){
        return residenteRepository.update(model);
    }

    public boolean delete(long id){
        return residenteRepository.delete(id);
    }
}
