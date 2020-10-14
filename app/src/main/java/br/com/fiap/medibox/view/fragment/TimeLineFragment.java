package br.com.fiap.medibox.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.adapter.TimeLineAdapter;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.ItemTimeline;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.viewModel.TimeLineViewModel;

public class TimeLineFragment extends Fragment {
    private View view;
    private Context context;
    private TimeLineAdapter adapter;
    private RecyclerView recyclerView;
    private TimeLineViewModel viewModel;
    private List<TimeLineModel> listTimeLine = new ArrayList<TimeLineModel>();
    private List<MedicamentoModel> listMedicamento = new ArrayList<MedicamentoModel>();
    private List<ResidenteModel> listResidente = new ArrayList<ResidenteModel>();
    private List<ResidenteMedicamentoModel> listResidenteMedicamento = new ArrayList<ResidenteMedicamentoModel>();
    private List<ItemTimeline> listItems = new ArrayList<ItemTimeline>();
    private ItemTimeline itemTimeline;
    private List<GavetaModel> listGaveta = new ArrayList<GavetaModel>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_lista_timeline, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        view = getView();
        context = getContext();
        backButton();
        initialization();
        obterDados();
    }

    public void initialization(){

        viewModel = new ViewModelProvider(this).get(TimeLineViewModel.class);
        recyclerView = (RecyclerView) view.findViewById(R.id.lista_timeline);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void obterDados(){
        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<TimeLineModel>>() {
            @Override
            public void onChanged(List<TimeLineModel> timeLineModels) {
                listTimeLine = timeLineModels;
                viewModel.getListResidenteMedicamento().observe(getViewLifecycleOwner(), new Observer<List<ResidenteMedicamentoModel>>() {
                    @Override
                    public void onChanged(List<ResidenteMedicamentoModel> residenteMedicamentoModels) {
                        listResidenteMedicamento = residenteMedicamentoModels;
                        viewModel.getListResidente().observe(getViewLifecycleOwner(), new Observer<List<ResidenteModel>>() {
                            @Override
                            public void onChanged(List<ResidenteModel> residenteModels) {
                                listResidente = residenteModels;
                                viewModel.getListMedicamento().observe(getViewLifecycleOwner(), new Observer<List<MedicamentoModel>>() {
                                    @Override
                                    public void onChanged(List<MedicamentoModel> medicamentoModels) {
                                        listMedicamento = medicamentoModels;
                                        viewModel.getListGaveta().observe(getViewLifecycleOwner(), new Observer<List<GavetaModel>>() {
                                            @Override
                                            public void onChanged(List<GavetaModel> gavetaModels) {
                                                listGaveta = gavetaModels;
                                                createBase();
                                            }
                                        });

                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

    }

    public void createBase(){
        listItems = new ArrayList<ItemTimeline>();
        for(TimeLineModel timeLineModel : listTimeLine){
            for(ResidenteMedicamentoModel residenteMedicamentoModel : listResidenteMedicamento){
                if(timeLineModel.getIdResidenteMedicamento() == residenteMedicamentoModel.getIdResidenteMedicamento()){
                    for (ResidenteModel residenteModel : listResidente){
                        if(residenteMedicamentoModel.getIdResidente() == residenteModel.getIdResidente()){
                            for(MedicamentoModel medicamentoModel : listMedicamento){
                                if(medicamentoModel.getIdMedicamento() == residenteMedicamentoModel.getIdMedicamento()){
                                    for(GavetaModel gavetaModel: listGaveta){
                                        if(gavetaModel.getIdGaveta() == medicamentoModel.getIdGaveta()) {
                                            medicamentoModel.setGaveta(gavetaModel);
                                            timeLineModel.setResidenteMedicamento(residenteMedicamentoModel);
                                            timeLineModel.setResidente(residenteModel);
                                            timeLineModel.setMedicamentoModel(medicamentoModel);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            listItems.add(converterModel(timeLineModel));
        }
        populate();
    }

    private ItemTimeline converterModel(TimeLineModel model){
        ItemTimeline item = new ItemTimeline();
        item.setIdTimeline(model.getIdTimeLine());
        item.setDose(model.getResidenteMedicamento().getDosagem());
        item.setDataHora(model.getDataHoraMedicacao());
        item.setMedicamento(model.getMedicamentoModel().getNomeMedicamento());
        item.setNome(model.getResidente().getNomeResidente());
        item.setGaveta(model.getMedicamentoModel().getGaveta().getNomeGaveta());
        item.setIntervalo(Double.toString(model.getResidenteMedicamento().getIntervalo()));
        item.setObservacao(model.getResidente().getObservacoes());
        item.setSituacao(model.getStatus());
        item.setTelResponsavel(model.getResidente().getTelResponsavel());
        return item;
    }

    public void populate(){
        adapter = new TimeLineAdapter(listItems, context);
        recyclerView.setAdapter(adapter);
    }

    //previne o botão voltar de ir pra tela de login ou sair do app
    public void backButton(){
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
