package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.fiap.medibox.model.Residente;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteRepository;

public class ResidenteViewModel extends AndroidViewModel {

    private ResidenteRepository residenteRepository;
    private ResidenteMedicamentoRepository residenteMedicamentoRepository;

    private Residente residente;
    private ResidenteModel residenteModel;

    private LiveData<List<ResidenteModel>> list;


    public ResidenteViewModel(@NonNull Application application) {
        super(application);
        residenteRepository = new ResidenteRepository(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
        //list = residenteRepository.getList();
    }


    public LiveData<List<ResidenteModel>> getListResidente(){
        //list = residenteRepository.getList();
        return list;
    }
}
