package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.model.Residente;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteRepository;

public class ResidenteViewModel extends AndroidViewModel {

    private ResidenteRepository residenteRepository;
    private ResidenteMedicamentoRepository residenteMedicamentoRepository;

    private Residente residente;
    private ResidenteModel residenteModel;

    private List<ResidenteModel> list = new ArrayList<ResidenteModel>();


    public ResidenteViewModel(@NonNull Application application) {
        super(application);
        residenteRepository = new ResidenteRepository(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
        list = residenteRepository.getList();
    }


    public List<ResidenteModel> getListResidente(){
        list = residenteRepository.getList();
        for (int i = 0; i<list.size(); i++){
            ResidenteModel model = list.get(i);
            List<ResidenteMedicamentoModel> medicamentos = residenteMedicamentoRepository.getListByIdResidente(model.getIdResidente());
        }

        return list;
    }
}
