package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;

public class ResidenteMedicamentoViewModel extends AndroidViewModel {

    private ResidenteMedicamentoRepository residenteMedicamentoRepository;
    private MutableLiveData<List<ResidenteMedicamentoModel>> list;
    private ResidenteMedicamentoModel residenteMedicamentoModel;

    public ResidenteMedicamentoViewModel(@NonNull Application application) {
        super(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
    }

    public LiveData<List<ResidenteMedicamentoModel>> getListResidente() {
        list = residenteMedicamentoRepository.getListService();
        return list;
    }

    public void saveList(List<ResidenteMedicamentoModel> lista) {
        residenteMedicamentoRepository.saveListDb(lista);
    }

    public boolean update(ResidenteMedicamentoModel model) {
        return residenteMedicamentoRepository.update(model);
    }

    public boolean delete(long id) {
        return residenteMedicamentoRepository.delete(id);
    }

}
