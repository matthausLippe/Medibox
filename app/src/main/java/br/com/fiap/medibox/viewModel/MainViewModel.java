package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.fiap.medibox.model.CaixaModel;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.EnderecoModel;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.repository.CaixaRepository;
import br.com.fiap.medibox.repository.ClienteRepository;
import br.com.fiap.medibox.repository.EnderecoRepository;
import br.com.fiap.medibox.repository.GavetaRepository;
import br.com.fiap.medibox.repository.MedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteMedicamentoRepository;
import br.com.fiap.medibox.repository.ResidenteRepository;
import br.com.fiap.medibox.repository.TimeLineRepository;

public class MainViewModel extends AndroidViewModel {

    private ResidenteRepository residenteRepository;
    private ClienteRepository clienteRepository;
    private MedicamentoRepository medicamentoRepository;
    private ResidenteMedicamentoRepository residenteMedicamentoRepository;
    private TimeLineRepository timeLineRepository;
    private CaixaRepository caixaRepository;
    private EnderecoRepository enderecoRepository;
    private GavetaRepository gavetaRepository;

    private MutableLiveData<List<ResidenteModel>> listResidente;
    private MutableLiveData<List<ClienteModel>> listCliente;
    private MutableLiveData<List<MedicamentoModel>> listMedicamento;
    private MutableLiveData<List<ResidenteMedicamentoModel>> listResidenteMedicamento;
    private MutableLiveData<List<TimeLineModel>> listTimeLine;
    private MutableLiveData<List<CaixaModel>> listCaixa;
    private MutableLiveData<List<GavetaModel>> listGaveta;
    private MutableLiveData<List<EnderecoModel>> listEndereco;


    public MainViewModel(@NonNull Application application) {
        super(application);
        residenteRepository = new ResidenteRepository(application);
        clienteRepository = new ClienteRepository(application);
        medicamentoRepository = new MedicamentoRepository(application);
        residenteMedicamentoRepository = new ResidenteMedicamentoRepository(application);
        timeLineRepository = new TimeLineRepository(application);
        enderecoRepository = new EnderecoRepository(application);
        caixaRepository = new CaixaRepository(application);
        gavetaRepository = new GavetaRepository(application);
    }

    public MutableLiveData<List<ResidenteModel>> getListResidenteService(){
        listResidente = residenteRepository.getListService();
        return listResidente;
    }

    public MutableLiveData<List<ClienteModel>> getListClienteService(){
        listCliente = clienteRepository.getListService();
        return listCliente;
    }

    public MutableLiveData<List<MedicamentoModel>> getListMedicamentoService(){
        listMedicamento = medicamentoRepository.getListService();
        return listMedicamento;
    }

    public MutableLiveData<List<ResidenteMedicamentoModel>> getListResidenteMedicamentoService(){
        listResidenteMedicamento = residenteMedicamentoRepository.getListService();
        return listResidenteMedicamento;
    }

    public MutableLiveData<List<TimeLineModel>> getListTimeLineService(){
        listTimeLine = timeLineRepository.getListService();
        return listTimeLine;
    }

    public MutableLiveData<List<CaixaModel>> getListCaixaService(){
        listCaixa = caixaRepository.getListService();
        return listCaixa;
    }

    public MutableLiveData<List<GavetaModel>> getListGavetaService(){
        listGaveta = gavetaRepository.getListService();
        return listGaveta;
    }

    public MutableLiveData<List<EnderecoModel>> getListEnderecoervice(){
        listEndereco = enderecoRepository.getListService();
        return listEndereco;
    }

    public void insertListResidente(List<ResidenteModel> list){
        residenteRepository.saveListDb(list);
    }

    public void insertListMedicamento(List<MedicamentoModel> list){
        medicamentoRepository.saveListDb(list);
    }

    public void insertListGaveta(List<GavetaModel> list){
        gavetaRepository.saveListDb(list);
    }

    public void insertListCaixa(List<CaixaModel> list){
        caixaRepository.saveListDb(list);
    }

    public void insertListCliente(List<ClienteModel> list){
        clienteRepository.saveListDb(list);
    }

    public void insertListResidenteMedicamento(List<ResidenteMedicamentoModel> list){
        residenteMedicamentoRepository.saveListDb(list);
    }

    public void insertListEndereco(List<EnderecoModel> list){
        enderecoRepository.saveListDb(list);
    }

    public void insertListTimeLine(List<TimeLineModel> list){
        timeLineRepository.saveListDb(list);
    }

}
