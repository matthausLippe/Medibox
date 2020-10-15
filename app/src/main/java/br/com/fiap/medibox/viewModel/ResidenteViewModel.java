package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.ResidenteWithCliente;
import br.com.fiap.medibox.repository.ClienteRepository;
import br.com.fiap.medibox.repository.MedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteRepository;


public class ResidenteViewModel extends AndroidViewModel {
    private ResidenteRepository residenteRepository;
    private ResidenteMedicamentoRepository residenteMedicamentoRepository;
    private ClienteRepository clienteRepository;
    private MedicamentoRepository medicamentoRepository;

    private ResidenteModel residenteModel;

    private MutableLiveData<List<ResidenteModel>> list = new MutableLiveData<>();
    private MutableLiveData<List<ClienteModel>> listaCliente = new MutableLiveData<>();
    private MutableLiveData<List<ResidenteMedicamentoModel>> listaResidenteMedicamentoModel = new MutableLiveData<>();
    private MutableLiveData<ResidenteModel> residenteModelMutable = new MutableLiveData<>();

    public ResidenteViewModel(@NonNull Application application) {
        super(application);
        residenteRepository = new ResidenteRepository(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
        clienteRepository = new ClienteRepository(application);
        medicamentoRepository = new MedicamentoRepository(application);
    }


    public LiveData<List<ResidenteModel>> getListResidente() {
        list = residenteRepository.getListService();
        return list;
    }

    public void saveList(List<ResidenteModel> lista) {
        residenteRepository.saveListDb(lista);
    }

    public MutableLiveData<Long> update(ResidenteModel model) {
        return residenteRepository.update(model);
    }

    public boolean delete(long id) {
        return residenteRepository.delete(id);
    }

    public LiveData<List<ResidenteModel>> getListResidenteDb(){
        list = residenteRepository.getListDb();
        return list;
    }


    public MutableLiveData<List<ClienteModel>> getCliente(){
        listaCliente = clienteRepository.getListDb();
        return listaCliente;
    }


    public MutableLiveData<List<ResidenteMedicamentoModel>> getListResidenteMedicamento(){
        listaResidenteMedicamentoModel = residenteMedicamentoRepository.getListService();
        return listaResidenteMedicamentoModel;
    }

    public MutableLiveData<ResidenteWithCliente> getResidenteWithCliente(long id){
        return residenteRepository.getResidenteWithCliente(id);
    }

    public MutableLiveData<ResidenteModel> getResidenteById(long id) {
        return residenteRepository.getById(id);
    }

    public MutableLiveData<ClienteModel> getClienteById(long id){
        return clienteRepository.getById(id);
    }

    public MutableLiveData<MedicamentoModel> getMedicamentoById(long id){
        return medicamentoRepository.getById(id);
    }

    public MutableLiveData<List<MedicamentoModel>> getListMedicamento(){
        return medicamentoRepository.getListService();
    }

    public boolean deleteResidenteMedicamento(long id){
        return residenteMedicamentoRepository.delete(id);
    }

}
